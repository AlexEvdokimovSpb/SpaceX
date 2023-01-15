package evdokimov.spacex.news.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import evdokimov.spacex.databinding.ItemNewsBinding

class NewsAdapter(val presenter: INewsListPresenter) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener { presenter.itemClickListener?.invoke(this) }
        }

    override fun getItemCount(): Int {
        val count = presenter.getCount()
        return count
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        presenter.bindView(holder.apply {
            pos = position
        })

    inner class ViewHolder(val vb: ItemNewsBinding) : RecyclerView.ViewHolder(vb.root), INewsItemView {
        override var pos = -1
        override fun setTitle(text: String) = with(vb) {
            tvTitle.text = text
        }

        override fun setDate(date: String) {
            vb.tvDate.text = date
        }
    }

    fun sortAscendingDate() {
        presenter.sortAscendingDateLoadedLaunches()
        notifyDataSetChanged()
    }

    fun sortDescendingDate() {
        presenter.sortDescendingDateLoadedLaunches()
        notifyDataSetChanged()
    }
}