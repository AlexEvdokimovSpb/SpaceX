package evdokimov.spacex.navigation

import com.github.terrakok.cicerone.Screen

interface IScreens {

    fun launches(): Screen
    fun launch(id: String): Screen
    fun authorisation(): Screen
    fun profile(): Screen
}