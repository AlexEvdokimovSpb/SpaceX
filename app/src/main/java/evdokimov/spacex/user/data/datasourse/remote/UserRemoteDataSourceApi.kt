package evdokimov.spacex.user.data.datasourse.remote

import evdokimov.spacex.user.data.entity.UserDto
import io.reactivex.rxjava3.core.Maybe

interface UserRemoteDataSourceApi {

    fun fetchUser(password: String, login: String): Maybe<UserDto>
}