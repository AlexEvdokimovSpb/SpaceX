package evdokimov.spacex.user.data.repository

import evdokimov.spacex.user.data.datasourse.local.UserLocalDataSourceApi
import evdokimov.spacex.user.data.datasourse.remote.UserRemoteDataSourceApi
import evdokimov.spacex.user.domain.entity.User
import io.reactivex.rxjava3.core.*

class UserRepository(
        private val userRemoteDataSource: UserRemoteDataSourceApi,
        private val userLocalDataSource: UserLocalDataSourceApi,
        private val userMapper: UserMapper
) : UserRepositoryApi {

    override val isUserExists: Flowable<Boolean>
        get() = userLocalDataSource.isUserExists

    override fun fetchUser(
            password: String,
            login: String
    ): Maybe<User> = userRemoteDataSource.fetchUser(
            password,
            login
    )
            .map { userDto ->
                userMapper.createUser(userDto)
            }

    override fun insertUser(user: User): Completable = userLocalDataSource.insertUser(userMapper.createUserEntity(user))

    override fun deleteUser(): Completable = userLocalDataSource.deleteUser()

    override fun getUser(): Single<User> = userLocalDataSource.getUser()
            .map { userMapper.createUser(it) }
}






