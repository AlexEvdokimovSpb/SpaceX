package evdokimov.spacex.favorites.data.repository

import evdokimov.spacex.favorites.domain.entity.FavoriteLaunch
import evdokimov.spacex.room.entity.FavoriteLaunchEntity

class FavoritesMapper {

    fun createFavoriteLaunch(favoriteLaunchEntity: FavoriteLaunchEntity): FavoriteLaunch = FavoriteLaunch(
            favoriteId = favoriteLaunchEntity.id
    )

    fun createFavoriteLaunchEntity(favoriteLaunch: FavoriteLaunch): FavoriteLaunchEntity = FavoriteLaunchEntity(
            id = favoriteLaunch.favoriteId
    )
}