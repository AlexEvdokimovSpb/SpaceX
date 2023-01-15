package evdokimov.spacex.user.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserDto(
    @Expose @SerializedName("name") val name: String,
    @Expose @SerializedName("email") val email: String?,
    @Expose @SerializedName("phone") val phone: String?
)