package ua.mrrobot1413.movies.base

import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import ua.mrrobot1413.movies.base.BaseViewHolder
import ua.mrrobot1413.movies.databinding.ItemLoadFooterBinding
import ua.mrrobot1413.movies.databinding.ItemViewLoadFooterBinding

class ExtendedFooterViewHolder(
    private val binding: ItemViewLoadFooterBinding
 ) : BaseViewHolder<LoadState>(binding.root) {

    override fun bind(item: LoadState) = Unit
}
