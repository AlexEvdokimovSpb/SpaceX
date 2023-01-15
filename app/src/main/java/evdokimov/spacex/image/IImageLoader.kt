package evdokimov.spacex.image

interface IImageLoader<T> {

    fun load(url: String, container: T)
}