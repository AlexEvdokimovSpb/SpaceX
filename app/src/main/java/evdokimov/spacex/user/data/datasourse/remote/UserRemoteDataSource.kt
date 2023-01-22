package evdokimov.spacex.user.data.datasourse.remote

import evdokimov.spacex.rx.SchedulerProviderContract
import evdokimov.spacex.user.data.api.UserApi
import evdokimov.spacex.user.data.entity.UserDto
import io.reactivex.rxjava3.core.Maybe

class UserRemoteDataSource(
        private val userApi: UserApi,
        private val scheduler: SchedulerProviderContract
) : UserRemoteDataSourceApi {

    override fun fetchUser(
            password: String,
            login: String
    ): Maybe<UserDto> = userApi.fetchUser(
            password,
            login
    )
            .subscribeOn(scheduler.io())
}

