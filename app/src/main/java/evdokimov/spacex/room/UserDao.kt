package evdokimov.spacex.room

import androidx.room.*
import evdokimov.spacex.room.entity.UserEntity
import io.reactivex.rxjava3.core.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity) : Completable

    @Query("DELETE FROM User")
    fun deleteUser() : Completable

    @Query("SELECT * FROM User WHERE id=1")
    fun getUser(): Single<UserEntity>

    @Query("SELECT EXISTS (SELECT 1 FROM User WHERE id=1)")
    fun isUserExists(): Single<Boolean>

}