package evdokimov.spacex.news.domain

import evdokimov.spacex.news.domain.entity.Launch

class NewsFunctions {

    fun filteringLoadedLaunches(
            loudedLaunches: List<Launch>
    ): List<Launch> = loudedLaunches.filter { it.links?.flickr?.original != null }
}