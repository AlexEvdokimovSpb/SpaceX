package evdokimov.spacex.room.entity

import androidx.room.*

@Entity(tableName = "FavoriteLaunch")
data class FavoriteLaunchEntity(
        @PrimaryKey @ColumnInfo(name = "id") val id: String
)
