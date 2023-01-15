package evdokimov.spacex.news.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Flickr(
    val original: String?
) : Parcelable