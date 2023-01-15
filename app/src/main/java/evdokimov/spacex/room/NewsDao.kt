package evdokimov.spacex.room

import androidx.room.*
import evdokimov.spacex.news.data.entity.LaunchRoom
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(launch: LaunchRoom): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg launch: LaunchRoom): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(launches: List<LaunchRoom>): Completable

    @Update
    fun update(launch: LaunchRoom): Completable

    @Update
    fun update(vararg launch: LaunchRoom): Completable

    @Update
    fun update(launches: List<LaunchRoom>): Completable

    @Delete
    fun delete(launch: LaunchRoom): Completable

    @Delete
    fun delete(vararg launch: LaunchRoom): Completable

    @Delete
    fun delete(launches: List<LaunchRoom>): Completable

    @Query("SELECT * FROM Launch")
    fun getAll(): Single<List<LaunchRoom>>
}