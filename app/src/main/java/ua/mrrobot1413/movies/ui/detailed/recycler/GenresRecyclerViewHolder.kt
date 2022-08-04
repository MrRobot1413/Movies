package ua.mrrobot1413.movies.ui.detailed.recycler

import ua.mrrobot1413.movies.base.BaseViewHolder
import ua.mrrobot1413.movies.data.network.model.Genre
import ua.mrrobot1413.movies.databinding.ItemGenreBinding

class GenresRecyclerViewHolder(private val binding: ItemGenreBinding): BaseViewHolder<Genre>(binding.root) {
    override fun bind(item: Genre) {
        binding.run {
            textView.text = item.name
        }
    }
}