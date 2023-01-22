package evdokimov.spacex.user.data.datasourse.local

import evdokimov.spacex.room.UserDao
import evdokimov.spacex.room.entity.UserEntity
import evdokimov.spacex.rx.SchedulerProviderContract
import io.reactivex.rxjava3.core.*

class UserLocalDataSource(
        private val userDao: UserDao,
        private val scheduler: SchedulerProviderContract
) : UserLocalDataSourceApi {

    override val isUserExists: Flowable<Boolean>
        get() = userDao.isUserExists()
                .subscribeOn(scheduler.computation())

    override fun insertUser(user: UserEntity): Completable = userDao.insertUser(user)
            .subscribeOn(scheduler.computation())

    override fun deleteUser(): Completable = userDao.deleteUser()
            .subscribeOn(scheduler.computation())

    override fun getUser(): Single<UserEntity> = userDao.getUser()
            .subscribeOn(scheduler.computation())
}

