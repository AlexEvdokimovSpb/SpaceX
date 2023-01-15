package evdokimov.spacex.user.data.datasourse.local

import evdokimov.spacex.room.entity.UserEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface UserLocalDataSourceApi {

    fun insertUser(user: UserEntity): Completable

    fun deleteUser(): Completable

    fun getUser(): Single<UserEntity>

    fun isUserExists(): Single<Boolean>
}