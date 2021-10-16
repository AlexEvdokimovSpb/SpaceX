package evdokimov.spacex.mvp.view.list

interface ILaunchItemView : IItemView {
    fun setTitle(text: String)
    fun setDate(date: String)
}