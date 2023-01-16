package evdokimov.spacex.user.data.repository

import evdokimov.spacex.user.data.entity.UserDto
import evdokimov.spacex.user.domain.entity.User

class UserMapper {

    fun createUser(userDto: UserDto): User = User(
        name = userDto.name, email = userDto.email, phone = userDto.phone
    )
}