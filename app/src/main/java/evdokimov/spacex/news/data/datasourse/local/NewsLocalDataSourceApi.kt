package evdokimov.spacex.news.data.datasourse.local

import evdokimov.spacex.room.entity.LaunchEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface NewsLocalDataSourceApi {

    fun putLaunches(launches: List<LaunchEntity>): Completable

    fun getLaunches(): Flowable<List<LaunchEntity>>

    fun clear(): Completable
}