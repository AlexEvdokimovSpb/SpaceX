package evdokimov.spacex.room.entity

import androidx.room.Embedded

data class LinksEntity(
    @Embedded(prefix = "flickr") val flickr: FlickrEntity?
)