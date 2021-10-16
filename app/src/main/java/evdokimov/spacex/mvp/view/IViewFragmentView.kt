package evdokimov.spacex.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface IViewFragmentView : MvpView {
    fun setName(name: String)
    fun loadImage(url: String)
    fun setDate(date: String)
    fun setDetails(details: String)
}