package evdokimov.spacex.mvp.model

interface IImageLoader<T> {
    fun load(url: String, container: T)
}