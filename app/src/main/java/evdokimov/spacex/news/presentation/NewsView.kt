package evdokimov.spacex.news.presentation

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface NewsView  : MvpView {
    fun init()
    fun updateList()
}