package evdokimov.spacex.user.data.datasourse.remote

import evdokimov.spacex.user.data.api.UserApi
import evdokimov.spacex.user.data.entity.UserDto
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.schedulers.Schedulers

class UserRemoteDataSource(
        private val userApi: UserApi
) : UserRemoteDataSourceApi {

    override fun fetchUser(
            password: String,
            login: String
    ): Maybe<UserDto> = userApi.fetchUser(
            password,
            login
    )
            .subscribeOn(Schedulers.computation())
}

