package evdokimov.spacex.room

import androidx.room.*
import evdokimov.spacex.room.entity.LaunchEntity
import io.reactivex.rxjava3.core.*

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(launches: List<LaunchEntity>): Completable

    @Query("SELECT * FROM Launch")
    fun getAll(): Flowable<List<LaunchEntity>>

    @Query("SELECT * FROM Launch WHERE id LIKE :id LIMIT 1")
    fun get(id: String): Flowable<LaunchEntity>

    @Query("DELETE FROM Launch")
    fun clear(): Completable
}