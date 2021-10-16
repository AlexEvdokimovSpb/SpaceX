package evdokimov.spacex.mvp.model.repo

import evdokimov.spacex.mvp.model.INetworkStatus
import evdokimov.spacex.mvp.model.api.IDataSource
import evdokimov.spacex.mvp.model.cache.ILaunchesCache
import evdokimov.spacex.mvp.model.entity.Launch
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

private const val START_YEAR = 2015
private const val END_YEAR = 2019

class RetrofitSpaceXRepo(
    val api: IDataSource,
    val networkStatus: INetworkStatus,
    val cache: ILaunchesCache,
) : ISpaceXRepo {

    override fun getLaunches() = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            api.getLaunches().flatMap { launches ->
                val loadedLaunches = filteringLoadedLaunches(launches)
                cache.putLaunches(loadedLaunches)
                Single.just(loadedLaunches)
            }
        } else {
            cache.getLaunches()
        }
    }.subscribeOn(Schedulers.io())

    private fun filteringLoadedLaunches(loudedLaunches: List<Launch>): List<Launch> {
        val filteredLaunches: List<Launch> = loudedLaunches.filter {
            (START_YEAR <= it.date_utc?.dropLast(20)?.toInt()!!)
                    && (END_YEAR >= it.date_utc.dropLast(20).toInt())
                    && (it.success == true)
        }
        return sortLoadedLaunches(filteredLaunches)
    }

    private fun sortLoadedLaunches(loudedLaunches: List<Launch>): List<Launch> {
        val sortedLaunches: List<Launch> = loudedLaunches.sortedWith(
            Comparator { launch1, launch2 ->
                launch1.date_utc?.dropLast(14)?.replace('-', '0')?.toInt()?.let {
                    launch2.date_utc?.dropLast(14)?.replace('-', '0')?.toInt()
                        ?.minus(it)
                }!!
            }
        )
        return sortedLaunches
    }
}






