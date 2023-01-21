package evdokimov.spacex.news.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import evdokimov.spacex.R
import evdokimov.spacex.base.BaseAdapter
import evdokimov.spacex.base.BaseViewHolder
import evdokimov.spacex.databinding.ItemNewsBinding
import evdokimov.spacex.news.domain.entity.Launch

class NewsAdapter : BaseAdapter<Launch>() {

    var onClickListener: ((Launch) -> Unit)? = null
    var onFavoriteIconClick: ((Launch) -> Unit)? = null

    override val areItemsTheSameCallback: (Launch, Launch) -> Boolean = { first, second ->
        first == second
    }

    override val areContentsTheSameCallback: (Launch, Launch) -> Boolean = { first, second ->
        first == second
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): BaseViewHolder<ItemNewsBinding, Launch> = LayoutInflater.from(parent.context)
            .let { inflater ->
                ItemNewsBinding.inflate(
                        inflater,
                        parent,
                        false
                )
            }
            .let { binding ->
                ViewHolder(
                        binding,
                        onClickListener,
                        onFavoriteIconClick
                )
            }

    class ViewHolder(
            binding: ItemNewsBinding,
            private val onClickListener: ((Launch) -> Unit)?,
            private val onFavoriteIconClick: ((Launch) -> Unit)?
    ) : BaseViewHolder<ItemNewsBinding, Launch>(binding) {

        override fun bind(
                item: Launch,
                position: Int
        ) = with(binding) {

            launchTitle.text = item.name
            launchDate.text = item.dateUtc

            if (item.isFavorite) {
                favoriteImage.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                favoriteImage.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }

            root.setOnClickListener {
                onClickListener?.invoke(item)
            }

            favoriteImage.setOnClickListener {
                onFavoriteIconClick?.invoke(item)
            }
        }
    }
}