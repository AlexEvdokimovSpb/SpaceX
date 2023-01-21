package evdokimov.spacex.navigation

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import evdokimov.spacex.authorisation.presentation.AuthorisationFragment
import evdokimov.spacex.details.presentation.DetailsFragment
import evdokimov.spacex.news.presentation.NewsFragment
import evdokimov.spacex.user.presentation.UserFragment

class Screens : IScreens {

    override fun launches(): Screen = FragmentScreen { NewsFragment.newInstance() }
    override fun launch(id: String): Screen = FragmentScreen { DetailsFragment.newInstance(id) }
    override fun authorisation(): Screen = FragmentScreen { AuthorisationFragment.newInstance() }
    override fun profile(): Screen = FragmentScreen { UserFragment.newInstance() }
}