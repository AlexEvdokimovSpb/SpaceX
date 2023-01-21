package evdokimov.spacex.room

import androidx.room.*
import evdokimov.spacex.room.entity.FavoriteLaunchEntity
import io.reactivex.rxjava3.core.*

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM FavoriteLaunch")
    fun getAll(): Flowable<List<FavoriteLaunchEntity>>

    @Query("DELETE FROM FavoriteLaunch WHERE id LIKE :id")
    fun delete(id: String): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteLaunch: FavoriteLaunchEntity): Completable

    @Query("DELETE FROM FavoriteLaunch")
    fun clear(): Completable
}