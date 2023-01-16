package evdokimov.spacex.user.domain

import evdokimov.spacex.user.data.repository.UserRepositoryApi
import evdokimov.spacex.user.domain.entity.User
import io.reactivex.rxjava3.core.Maybe

class UserInteractor(
    private val userRepository: UserRepositoryApi
) {

    fun fetchUser(password: String, login: String): Maybe<User> = userRepository.fetchUser(
        password, login
    )
}