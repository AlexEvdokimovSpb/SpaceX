package evdokimov.spacex.user.presentation

import evdokimov.spacex.base.BaseMvpView
import evdokimov.spacex.user.domain.entity.User
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface UserView : BaseMvpView {

    fun setUser(user: User)
}