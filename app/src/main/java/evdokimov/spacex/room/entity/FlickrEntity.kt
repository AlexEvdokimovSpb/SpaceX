package evdokimov.spacex.room.entity

import androidx.room.ColumnInfo

data class FlickrEntity(
    @ColumnInfo(name = "original") val original: String?
)