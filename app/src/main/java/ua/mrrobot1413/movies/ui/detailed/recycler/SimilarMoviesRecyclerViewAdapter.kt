package ua.mrrobot1413.movies.ui.detailed.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ua.mrrobot1413.movies.data.network.model.Movie
import ua.mrrobot1413.movies.databinding.ItemMovieBinding

class SimilarMoviesRecyclerViewAdapter(
    private val onItemClicked: (Int) -> Unit
) :
    ListAdapter<Movie, SimilarMoviesRecyclerViewHolder>(object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Movie,
            newItem: Movie
        ): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMoviesRecyclerViewHolder {
        return SimilarMoviesRecyclerViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SimilarMoviesRecyclerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClicked(item.id)
        }
    }
}