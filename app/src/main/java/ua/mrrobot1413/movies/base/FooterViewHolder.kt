package ua.mrrobot1413.movies.base

import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import ua.mrrobot1413.movies.databinding.ItemLoadFooterBinding

class FooterViewHolder(
    private val binding: ItemLoadFooterBinding
 ) : BaseViewHolder<LoadState>(binding.root) {

    override fun bind(item: LoadState) = Unit
}
