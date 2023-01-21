package evdokimov.spacex.user.data.datasourse.local

import evdokimov.spacex.room.entity.UserEntity
import io.reactivex.rxjava3.core.*

interface UserLocalDataSourceApi {

    val isUserExists: Flowable<Boolean>

    fun insertUser(user: UserEntity): Completable

    fun deleteUser(): Completable

    fun getUser(): Single<UserEntity>
}