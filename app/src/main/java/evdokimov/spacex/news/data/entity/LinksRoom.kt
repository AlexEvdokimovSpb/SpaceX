package evdokimov.spacex.news.data.entity

import androidx.room.Embedded

data class LinksRoom(
    @Embedded(prefix = "flickr") val flickr: FlickrRoom?
)