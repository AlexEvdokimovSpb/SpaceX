package evdokimov.spacex.news.data.repository

import evdokimov.spacex.news.data.entity.*
import evdokimov.spacex.news.domain.entity.*

class NewsMapper {

    fun createLaunch(launchRoom: LaunchRoom): Launch = Launch(
        links = createLinks(launchRoom.links),
        success = launchRoom.success,
        details = launchRoom.details,
        flightNumber = launchRoom.flightNumber,
        name = launchRoom.name,
        dateUtc = launchRoom.dateUtc,
        id = launchRoom.id
    )

    private fun createLinks(linksRoom: LinksRoom?): Links = Links(
        flickr = createFlickr(linksRoom?.flickr)
    )

    private fun createFlickr(flickrRoom: FlickrRoom?): Flickr = Flickr(
        original = flickrRoom?.original
    )

    fun createLaunchRoom(launchDto: LaunchDto): LaunchRoom = LaunchRoom(
        links = createLinksRoom(launchDto.links),
        success = launchDto.success,
        details = launchDto.details,
        flightNumber = launchDto.flightNumber,
        name = launchDto.name,
        dateUtc = launchDto.dateUtc,
        id = launchDto.id
    )

    private fun createLinksRoom(linksDto: LinksDto?): LinksRoom = LinksRoom(
        flickr = createFlickrRoom(linksDto?.flickr)
    )

    private fun createFlickrRoom(flickrDto: FlickrDto?): FlickrRoom = FlickrRoom(
        original = flickrDto?.original?.firstOrNull()
    )
}