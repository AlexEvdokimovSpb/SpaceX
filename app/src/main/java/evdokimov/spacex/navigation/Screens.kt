package evdokimov.spacex.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import evdokimov.spacex.details.presentation.DetailsFragment
import evdokimov.spacex.news.domain.entity.Launch
import evdokimov.spacex.news.presentation.NewsFragment

class Screens : IScreens {

    override fun launches() = FragmentScreen { NewsFragment.newInstance() }
    override fun launch(launch: Launch) = FragmentScreen { DetailsFragment.newInstance(launch) }
}