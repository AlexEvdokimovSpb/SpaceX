package evdokimov.spacex.news.presentation.list

interface IListPresenter<V> {

    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
    fun sortAscendingDateLoadedLaunches()
    fun sortDescendingDateLoadedLaunches()
}