package evdokimov.spacex.news.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LinksDto(
    @Expose @SerializedName("flickr") val flickr: FlickrDto?
)