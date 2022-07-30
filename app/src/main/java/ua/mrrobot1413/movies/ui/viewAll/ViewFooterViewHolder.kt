package ua.mrrobot1413.movies.ui.viewAll

import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import ua.mrrobot1413.movies.base.BaseViewHolder
import ua.mrrobot1413.movies.databinding.ItemLoadFooterBinding
import ua.mrrobot1413.movies.databinding.ItemViewLoadFooterBinding

class ViewFooterViewHolder(
    private val binding: ItemViewLoadFooterBinding
 ) : BaseViewHolder<LoadState>(binding.root) {

    override fun bind(item: LoadState) = Unit
}
