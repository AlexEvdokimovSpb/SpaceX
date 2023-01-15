package evdokimov.spacex.news.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Links(
    val flickr: Flickr?
) : Parcelable