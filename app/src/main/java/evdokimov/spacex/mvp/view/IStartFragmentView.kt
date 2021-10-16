package evdokimov.spacex.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface IStartFragmentView  : MvpView {
    fun init()
    fun updateList()
}