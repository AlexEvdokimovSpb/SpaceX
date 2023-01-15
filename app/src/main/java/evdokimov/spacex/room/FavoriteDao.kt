package evdokimov.spacex.room

import androidx.room.*
import evdokimov.spacex.room.entity.FavoriteLaunchEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM FavoriteLaunch")
    fun getAll(): Single<List<FavoriteLaunchEntity>>

    @Query("SELECT * FROM FavoriteLaunch WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): Single<FavoriteLaunchEntity>

    @Query("DELETE FROM FavoriteLaunch WHERE id LIKE :id")
    fun deleteById(id: Int): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg favoriteLaunch: FavoriteLaunchEntity): Completable

    @Query("DELETE FROM FavoriteLaunch")
    fun clear(): Completable
}