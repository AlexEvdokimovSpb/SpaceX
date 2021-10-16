package evdokimov.spacex.mvp.model.room

import androidx.room.*

@Dao
interface ISpaceXDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(launch: LaunchRoom)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg launch: LaunchRoom)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(launches: List<LaunchRoom>)

    @Update
    fun update(launch: LaunchRoom)

    @Update
    fun update(vararg launch: LaunchRoom)

    @Update
    fun update(launches: List<LaunchRoom>)

    @Delete
    fun delete(launch: LaunchRoom)

    @Delete
    fun delete(vararg launch: LaunchRoom)

    @Delete
    fun delete(launches: List<LaunchRoom>)

    @Query("SELECT * FROM LaunchRoom")
    fun getAll(): List<LaunchRoom>
}