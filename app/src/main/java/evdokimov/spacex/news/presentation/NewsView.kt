package evdokimov.spacex.news.presentation

import evdokimov.spacex.base.BaseMvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface NewsView : BaseMvpView {

    fun init()
    fun updateList()
}