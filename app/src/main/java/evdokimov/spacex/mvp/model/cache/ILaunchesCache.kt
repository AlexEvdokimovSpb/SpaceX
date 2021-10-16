package evdokimov.spacex.mvp.model.cache

import evdokimov.spacex.mvp.model.entity.Launch
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface ILaunchesCache {
    fun putLaunches(launches: List<Launch>): Completable
    fun getLaunches(): Single<List<Launch>>
}