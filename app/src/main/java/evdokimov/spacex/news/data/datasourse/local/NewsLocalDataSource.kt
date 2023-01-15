package evdokimov.spacex.news.data.datasourse.local

import evdokimov.spacex.news.data.entity.LaunchRoom
import evdokimov.spacex.room.Database
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class NewsLocalDataSource(
    val db: Database
) : NewsLocalDataSourceApi {

    override fun putLaunches(launches: List<LaunchRoom>): Completable = db.spaceXDAO.insert(launches)

    override fun getLaunches(): Single<List<LaunchRoom>> = db.spaceXDAO.getAll()
}
