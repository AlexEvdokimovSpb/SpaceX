package evdokimov.spacex.details.presentation

import evdokimov.spacex.base.BaseMvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface DetailsView : BaseMvpView {

    fun setName(name: String)
    fun loadImage(url: String)
    fun setDate(date: String)
    fun setDetails(details: String)
}