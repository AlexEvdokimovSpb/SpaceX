package evdokimov.spacex.user.domain

import evdokimov.spacex.user.data.repository.UserRepositoryApi
import evdokimov.spacex.user.domain.entity.User
import io.reactivex.rxjava3.core.*

class UserInteractor(
        private val userRepository: UserRepositoryApi
) {

    val isUserExists: Flowable<Boolean>
        get() = userRepository.isUserExists

    fun fetchUser(
            password: String,
            login: String
    ): Maybe<User> = userRepository.fetchUser(
            password,
            login
    )

    fun insertUser(user: User): Completable = userRepository.insertUser(user)

    fun deleteUser(): Completable = userRepository.deleteUser()

    fun getUser(): Single<User> = userRepository.getUser()
}