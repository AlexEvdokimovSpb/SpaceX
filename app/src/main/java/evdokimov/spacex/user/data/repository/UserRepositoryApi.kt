package evdokimov.spacex.user.data.repository

import evdokimov.spacex.user.domain.entity.User
import io.reactivex.rxjava3.core.Maybe

interface UserRepositoryApi {

    fun fetchUser(password: String, login: String): Maybe<User>
}