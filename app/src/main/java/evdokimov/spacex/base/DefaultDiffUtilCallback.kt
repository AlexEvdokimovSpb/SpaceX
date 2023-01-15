package evdokimov.spacex.base

import androidx.recyclerview.widget.DiffUtil

class DefaultDiffUtilCallback<T : Any>(
        private val oldItems: List<T>,
        private val newItems: List<T>,
        private val areItemsTheSameCallback: (T, T) -> Boolean,
        private val areContentsTheSameCallback: (T, T) -> Boolean,
        private val payloadChangedCallback: ((T, T) -> Any?)?
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return areItemsTheSameCallback(oldItem, newItem)
    }

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return areContentsTheSameCallback(oldItem, newItem)
    }

    override fun getChangePayload(
            oldItemPosition: Int,
            newItemPosition: Int
    ): Any? = payloadChangedCallback ?: super.getChangePayload(oldItemPosition, newItemPosition)

}