package evdokimov.spacex.news.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LaunchDto(
    @Expose @SerializedName("links") val links: LinksDto?,
    @Expose @SerializedName("success") val success: Boolean?,
    @Expose @SerializedName("details") val details: String?,
    @Expose @SerializedName("flight_number") val flightNumber: Int?,
    @Expose @SerializedName("name") val name: String?,
    @Expose @SerializedName("date_utc") val dateUtc: String?,
    @Expose @SerializedName("id") val id: String
)

data class ShortLaunchDto(
    @Expose @SerializedName("flight_number") val flightNumber: Int?,
    @Expose @SerializedName("name") val name: String?,
    @Expose @SerializedName("date_utc") val dateUtc: String?,
    @Expose @SerializedName("id") val id: String
)