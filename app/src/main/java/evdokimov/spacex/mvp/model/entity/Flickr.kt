package evdokimov.spacex.mvp.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
data class Flickr(
    @Expose val original: Array<String>?
) : Parcelable