package evdokimov.spacex.navigation

import com.github.terrakok.cicerone.Screen
import evdokimov.spacex.news.domain.entity.Launch

interface IScreens {

    fun launches(): Screen
    fun launch(launch: Launch): Screen
    fun authorisation(): Screen
    fun profile(): Screen
}