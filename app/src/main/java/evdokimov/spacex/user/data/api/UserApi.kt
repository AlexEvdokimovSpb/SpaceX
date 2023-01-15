package evdokimov.spacex.user.data.api

import evdokimov.spacex.user.data.entity.UserDto
import io.reactivex.rxjava3.core.Maybe

interface UserApi {

    fun fetchUser(password: String, login: String): Maybe<UserDto>
}