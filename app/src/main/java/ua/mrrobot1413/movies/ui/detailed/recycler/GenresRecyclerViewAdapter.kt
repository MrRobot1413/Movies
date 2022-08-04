package ua.mrrobot1413.movies.ui.detailed.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ua.mrrobot1413.movies.base.BasePagingDataAdapter
import ua.mrrobot1413.movies.base.BaseViewHolder
import ua.mrrobot1413.movies.data.network.model.Genre
import ua.mrrobot1413.movies.data.network.model.Movie
import ua.mrrobot1413.movies.databinding.ItemGenreBinding
import ua.mrrobot1413.movies.databinding.ItemMovieBinding
import java.math.BigInteger

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