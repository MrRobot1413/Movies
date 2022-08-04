package ua.mrrobot1413.movies.base

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ua.mrrobot1413.movies.data.network.model.Movie
import java.math.BigInteger

abstract class BasePagingDataAdapter<T : Any, VH: BaseViewHolder<T>>(
    private val diffUtil: DiffUtil.ItemCallback<T>,
    private val onItemClicked: (Int) -> Unit = {}
) :
    PagingDataAdapter<T, BaseViewHolder<T>>(diffUtil) {

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            if(item is Movie) {
                onItemClicked(item.id)
            }
        }
        item?.let { holder.bind(it) }
    }
}