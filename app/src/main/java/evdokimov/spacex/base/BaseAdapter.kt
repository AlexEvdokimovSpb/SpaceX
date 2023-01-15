package evdokimov.spacex.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : Any> : RecyclerView.Adapter<BaseViewHolder<*, T>>() {

    abstract val areItemsTheSameCallback: (T, T) -> Boolean
    abstract val areContentsTheSameCallback: (T, T) -> Boolean

    private var payloadChangedCallback: ((T, T) -> Any?)? = null

    protected val items: MutableList<T> = mutableListOf()

    fun setPayloadChangedCallback(callback: ((T, T) -> Any?)?) {
        payloadChangedCallback = callback
    }

    fun setData(items: List<T>) {
        val diffResult = DiffUtil.calculateDiff(DefaultDiffUtilCallback(
                oldItems = this.items,
                newItems = items,
                areItemsTheSameCallback = areItemsTheSameCallback,
                areContentsTheSameCallback = areContentsTheSameCallback,
                payloadChangedCallback = payloadChangedCallback
        ))
        this.items.clear()
        this.items.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<*, T>, position: Int) {
        val item = items[position]
        holder.bind(item, position)
    }

}