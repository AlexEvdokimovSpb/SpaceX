package evdokimov.spacex.mvp.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
data class Launch(
    @Expose val links: Links?,
    @Expose val success: Boolean?,
    @Expose val details: String?,
    @Expose val flight_number: Int?,
    @Expose val name: String?,
    @Expose val date_utc: String?,
    @Expose val id: String
) : Parcelable