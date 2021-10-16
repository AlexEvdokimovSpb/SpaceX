package evdokimov.spacex.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import evdokimov.spacex.databinding.ItemBinding
import evdokimov.spacex.mvp.presenter.list.ILaunchesListPresenter
import evdokimov.spacex.mvp.view.list.ILaunchItemView

class RVAdapter(val presenter: ILaunchesListPresenter) :
    RecyclerView.Adapter<RVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemBinding.inflate(
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

    inner class ViewHolder(val vb: ItemBinding) : RecyclerView.ViewHolder(vb.root),
        ILaunchItemView {
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