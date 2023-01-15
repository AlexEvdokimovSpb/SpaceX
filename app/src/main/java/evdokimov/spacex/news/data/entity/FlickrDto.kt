package evdokimov.spacex.news.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FlickrDto(
    @Expose @SerializedName("original") val original: ArrayList<String>?
)