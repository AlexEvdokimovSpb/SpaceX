package evdokimov.spacex.user.data.datasourse.local

import evdokimov.spacex.room.UserDao
import evdokimov.spacex.room.entity.UserEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class UserLocalDataSource(
    private val userDao: UserDao,
) : UserLocalDataSourceApi {

    override fun insertUser(user: UserEntity): Completable =
        userDao.insertUser(user).subscribeOn(Schedulers.computation())

    override fun deleteUser(): Completable = userDao.deleteUser().subscribeOn(Schedulers.computation())

    override fun getUser(): Single<UserEntity> = userDao.getUser().subscribeOn(Schedulers.computation())

    override fun isUserExists(): Single<Boolean> = userDao.isUserExists().subscribeOn(Schedulers.computation())
}

