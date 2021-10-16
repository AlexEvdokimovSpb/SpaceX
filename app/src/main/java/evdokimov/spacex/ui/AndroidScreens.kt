package evdokimov.spacex.ui

import com.github.terrakok.cicerone.androidx.FragmentScreen
import evdokimov.spacex.mvp.model.entity.Launch
import evdokimov.spacex.mvp.navigation.IScreens
import evdokimov.spacex.ui.fragment.StartFragment
import evdokimov.spacex.ui.fragment.ViewFragment

class AndroidScreens : IScreens {
    override fun launches() = FragmentScreen { StartFragment.newInstance() }
    override fun launch(launch: Launch) = FragmentScreen { ViewFragment.newInstance(launch) }
}