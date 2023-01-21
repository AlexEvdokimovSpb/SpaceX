package evdokimov.spacex.user.data.repository

import evdokimov.spacex.user.domain.entity.User
import io.reactivex.rxjava3.core.*

interface UserRepositoryApi {

    val isUserExists: Flowable<Boolean>

    fun fetchUser(
            password: String,
            login: String
    ): Maybe<User>

    fun insertUser(user: User): Completable

    fun deleteUser(): Completable

    fun getUser(): Single<User>
}