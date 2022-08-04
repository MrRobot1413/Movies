package ua.mrrobot1413.movies.ui.home.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ua.mrrobot1413.movies.base.BasePagingDataAdapter
import ua.mrrobot1413.movies.data.network.model.Movie
import ua.mrrobot1413.movies.databinding.ItemMovieBinding
import java.math.BigInteger

class UpcomingRecyclerViewAdapter(
    private val onItemClicked: (Int) -> Unit
) :
    BasePagingDataAdapter<Movie, UpcomingRecyclerViewHolder>(object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Movie,
            newItem: Movie
        ): Boolean {
            return oldItem == newItem
        }
    }, {
        onItemClicked(it)
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingRecyclerViewHolder {
        return UpcomingRecyclerViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}