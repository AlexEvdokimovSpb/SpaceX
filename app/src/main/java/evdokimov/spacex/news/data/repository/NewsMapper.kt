package evdokimov.spacex.news.data.repository

import evdokimov.spacex.news.data.entity.*
import evdokimov.spacex.news.domain.entity.*
import evdokimov.spacex.room.entity.*

class NewsMapper {

    fun createLaunch(
            launchEntity: LaunchEntity,
            isFavorite: Boolean = false
    ): Launch = Launch(
            links = createLinks(launchEntity.links),
            success = launchEntity.success,
            details = launchEntity.details,
            flightNumber = launchEntity.flightNumber,
            name = launchEntity.name,
            dateUtc = launchEntity.dateUtc,
            id = launchEntity.id,
            isFavorite = isFavorite
    )

    private fun createLinks(linksEntity: LinksEntity?): Links = Links(
            flickr = createFlickr(linksEntity?.flickr)
    )

    private fun createFlickr(flickrEntity: FlickrEntity?): Flickr = Flickr(
            original = flickrEntity?.original
    )

    fun createLaunchEntity(launchDto: LaunchDto): LaunchEntity = LaunchEntity(
            links = createLinksEntity(launchDto.links),
            success = launchDto.success,
            details = launchDto.details,
            flightNumber = launchDto.flightNumber,
            name = launchDto.name,
            dateUtc = launchDto.dateUtc,
            id = launchDto.id
    )

    fun createLaunchEntity(launchDto: ShortLaunchDto): LaunchEntity = LaunchEntity(
            links = createLinksEntity(launchDto.links),
            success = null,
            details = null,
            flightNumber = launchDto.flightNumber,
            name = launchDto.name,
            dateUtc = null,
            id = launchDto.id
    )

    private fun createLinksEntity(linksDto: LinksDto?): LinksEntity = LinksEntity(
            flickr = createFlickrEntity(linksDto?.flickr)
    )

    private fun createFlickrEntity(flickrDto: FlickrDto?): FlickrEntity = FlickrEntity(
            original = flickrDto?.original?.firstOrNull()
    )
}