package evdokimov.spacex.user.data.api

import evdokimov.spacex.user.data.entity.UserDto
import io.reactivex.rxjava3.core.Maybe

class UserMockServer : UserApi {

    private companion object {

        const val PASSWORD = "123456"
        const val LOGIN = "login"

        val userEntity = UserDto(
            name = "Ivan Ivanov", email = "ivan@mail.com", phone = "+7 911 111-11-11"
        )
    }

    override fun fetchUser(password: String, login: String): Maybe<UserDto> = Maybe.create { emitter ->
        try {
            if (password == PASSWORD && login == LOGIN) {
                emitter.onSuccess(userEntity)
            } else {
                emitter.onComplete()
            }
        } catch (t: Throwable) {
            emitter.onError(RuntimeException("Error $t"))
        }
    }
}