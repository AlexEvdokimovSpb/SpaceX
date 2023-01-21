package evdokimov.spacex.room

import androidx.room.*
import evdokimov.spacex.room.entity.LaunchEntity
import io.reactivex.rxjava3.core.*

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(launch: LaunchEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg launch: LaunchEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(launches: List<LaunchEntity>): Completable

    @Update
    fun update(launch: LaunchEntity): Completable

    @Update
    fun update(vararg launch: LaunchEntity): Completable

    @Update
    fun update(launches: List<LaunchEntity>): Completable

    @Delete
    fun delete(launch: LaunchEntity): Completable

    @Delete
    fun delete(vararg launch: LaunchEntity): Completable

    @Delete
    fun delete(launches: List<LaunchEntity>): Completable

    @Query("SELECT * FROM Launch")
    fun getAll(): Flowable<List<LaunchEntity>>

    @Query("DELETE FROM Launch")
    fun clear(): Completable
}