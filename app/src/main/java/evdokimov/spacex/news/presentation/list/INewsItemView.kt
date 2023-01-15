package evdokimov.spacex.news.presentation.list

interface INewsItemView : IItemView {

    fun setTitle(text: String)
    fun setDate(date: String)
}