package evdokimov.spacex.news.data.entity

import androidx.room.ColumnInfo

data class FlickrRoom(
    @ColumnInfo(name = "original") val original: String?
)