package evdokimov.spacex.mvp.model.cache

import evdokimov.spacex.mvp.model.entity.Launch
import evdokimov.spacex.mvp.model.room.Database
import evdokimov.spacex.mvp.model.room.LaunchRoom
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class LaunchesCache(val db: Database) : ILaunchesCache {

    override fun putLaunches(launches: List<Launch>) = Completable.fromAction {
        val roomLaunches = launches.map { launch ->
            LaunchRoom(
                launch.success,
                launch.details,
                launch.flight_number,
                launch.name,
                launch.date_utc,
                launch.id
            )
        }
        db.spaceXDAO.insert(roomLaunches)
    }.subscribeOn(Schedulers.io())

    override fun getLaunches() = Single.fromCallable {
        db.spaceXDAO.getAll().map { roomLaunch ->
            Launch(
                links = null,
                roomLaunch.success,
                roomLaunch.details,
                roomLaunch.flight_number,
                roomLaunch.name,
                roomLaunch.date_utc,
                roomLaunch.id
            )
        }
    }.subscribeOn(Schedulers.io())
}

