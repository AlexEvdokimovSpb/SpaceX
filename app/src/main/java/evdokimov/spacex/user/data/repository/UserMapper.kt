package evdokimov.spacex.user.data.repository

import evdokimov.spacex.room.entity.UserEntity
import evdokimov.spacex.user.data.entity.UserDto
import evdokimov.spacex.user.domain.entity.User

class UserMapper {

    fun createUser(userDto: UserDto): User = User(
            name = userDto.name,
            email = userDto.email,
            phone = userDto.phone
    )

    fun createUser(userEntity: UserEntity): User = User(
            name = userEntity.name,
            email = userEntity.email,
            phone = userEntity.phone
    )

    fun createUserEntity(user: User): UserEntity = UserEntity(
            name = user.name,
            email = user.email,
            phone = user.phone
    )
}