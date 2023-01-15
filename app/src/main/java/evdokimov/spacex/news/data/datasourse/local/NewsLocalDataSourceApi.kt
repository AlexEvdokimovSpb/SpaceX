package evdokimov.spacex.news.data.datasourse.local

import evdokimov.spacex.news.data.entity.LaunchRoom
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface NewsLocalDataSourceApi {

    fun putLaunches(launches: List<LaunchRoom>): Completable

    fun getLaunches(): Single<List<LaunchRoom>>
}