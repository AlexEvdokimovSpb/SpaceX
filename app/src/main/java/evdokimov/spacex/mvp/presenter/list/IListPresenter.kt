package evdokimov.spacex.mvp.presenter.list

interface IListPresenter<V> {
    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
    fun sortAscendingDateLoadedLaunches()
    fun sortDescendingDateLoadedLaunches()
}