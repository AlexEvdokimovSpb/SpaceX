package evdokimov.spacex.mvp.navigation

import com.github.terrakok.cicerone.Screen
import evdokimov.spacex.mvp.model.entity.Launch

interface IScreens {
    fun launches(): Screen
    fun launch(launch: Launch): Screen
}