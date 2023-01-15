package evdokimov.spacex.room.entity

import androidx.room.*

@Entity(tableName = "FavoriteLaunch")
data class FavoriteLaunchEntity(
    @PrimaryKey @ColumnInfo(name = "favorite_id") val favoriteId: Int,
    @ColumnInfo(name = "id") val id: Int
)
