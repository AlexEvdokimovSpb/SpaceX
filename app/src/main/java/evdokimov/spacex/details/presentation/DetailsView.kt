package evdokimov.spacex.details.presentation

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface DetailsView : MvpView {
    fun setName(name: String)
    fun loadImage(url: String)
    fun setDate(date: String)
    fun setDetails(details: String)
}