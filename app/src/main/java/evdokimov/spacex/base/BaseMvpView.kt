package evdokimov.spacex.base

import androidx.annotation.StringRes
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface BaseMvpView : MvpView {

    fun showMessage(@StringRes resId: Int) {}

    fun showMessage(message: String) {}
}