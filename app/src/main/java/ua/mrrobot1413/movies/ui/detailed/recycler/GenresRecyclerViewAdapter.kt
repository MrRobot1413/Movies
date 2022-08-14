package ua.mrrobot1413.movies.ui.detailed.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ua.mrrobot1413.movies.data.network.model.Genre
import ua.mrrobot1413.movies.databinding.ItemGenreBinding

class GenresRecyclerViewAdapter :
    ListAdapter<Genre, GenresRecyclerViewHolder>(object : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: Genre,
            newItem: Genre
        ): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresRecyclerViewHolder {
        return GenresRecyclerViewHolder(
            ItemGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GenresRecyclerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}