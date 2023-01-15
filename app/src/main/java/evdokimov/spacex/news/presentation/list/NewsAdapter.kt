package evdokimov.spacex.news.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import evdokimov.spacex.base.BaseAdapter
import evdokimov.spacex.base.BaseViewHolder
import evdokimov.spacex.databinding.ItemNewsBinding
import evdokimov.spacex.news.domain.entity.Launch

class NewsAdapter : BaseAdapter<Launch>() {

    var onClickListener: ((Launch) -> Unit)? = null

    override val areItemsTheSameCallback: (Launch, Launch) -> Boolean = { first, second ->
        first == second
    }

    override val areContentsTheSameCallback: (Launch, Launch) -> Boolean = { first, second ->
        first == second
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): BaseViewHolder<ItemNewsBinding, Launch> =
        LayoutInflater.from(parent.context).let { inflater -> ItemNewsBinding.inflate(inflater, parent, false) }
            .let { binding -> ViewHolder(binding, onClickListener) }

    class ViewHolder(
        binding: ItemNewsBinding, private val onClickListener: ((Launch) -> Unit)?
    ) : BaseViewHolder<ItemNewsBinding, Launch>(binding) {

        override fun bind(item: Launch, position: Int) = with(binding) {

            launchTitle.text = item.name
            launchDate.text = item.dateUtc

            root.setOnClickListener {
                onClickListener?.invoke(item)
            }
        }
    }
}