package evdokimov.spacex.news.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Launch(
    val links: Links?,
    val success: Boolean?,
    val details: String?,
    val flightNumber: Int?,
    val name: String?,
    val dateUtc: String?,
    val id: String,
    val isFavorite: Boolean
) : Parcelable