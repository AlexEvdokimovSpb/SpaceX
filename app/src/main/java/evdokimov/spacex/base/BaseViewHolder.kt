package evdokimov.spacex.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<VB : ViewBinding, T : Any>(protected val binding: VB) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: T, position: Int)
}