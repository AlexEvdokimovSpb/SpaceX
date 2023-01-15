package evdokimov.spacex.news.data.entity

import androidx.room.*

@Entity(tableName = "Launch")
data class LaunchRoom(
    @Embedded(prefix = "links") val links: LinksRoom?,
    @ColumnInfo(name = "success") val success: Boolean?,
    @ColumnInfo(name = "details") val details: String?,
    @ColumnInfo(name = "flight_number") val flightNumber: Int?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "date_utc") val dateUtc: String?,
    @PrimaryKey @ColumnInfo(name = "id") val id: String
)