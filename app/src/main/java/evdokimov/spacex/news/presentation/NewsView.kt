package evdokimov.spacex.news.presentation

import evdokimov.spacex.base.BaseMvpView
import evdokimov.spacex.news.domain.entity.Launch
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface NewsView : BaseMvpView {

    fun updateView(launches: List<Launch>)
}