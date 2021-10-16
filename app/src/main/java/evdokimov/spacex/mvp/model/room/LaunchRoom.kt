package evdokimov.spacex.mvp.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class LaunchRoom(
    val success: Boolean?,
    val details: String?,
    val flight_number: Int?,
    val name: String?,
    val date_utc: String?,
    @PrimaryKey
    val id: String,
)
