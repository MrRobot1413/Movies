package ua.mrrobot1413.movies.ui.reminders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ua.mrrobot1413.movies.data.network.model.MovieResponseModel
import ua.mrrobot1413.movies.data.storage.model.FavoriteMovie
import ua.mrrobot1413.movies.data.storage.model.Movie
import ua.mrrobot1413.movies.databinding.ItemFavoriteMovieBinding
import ua.mrrobot1413.movies.databinding.ItemMovieBinding
import ua.mrrobot1413.movies.ui.home.recycler.LatestRecyclerViewHolder

class ReminderRecyclerViewAdapter(
    private val onItemClicked: (Int) -> Unit
) :
    ListAdapter<FavoriteMovie, ReminderRecyclerViewHolder>(object : DiffUtil.ItemCallback<FavoriteMovie>() {
        override fun areItemsTheSame(oldItem: FavoriteMovie, newItem: FavoriteMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: FavoriteMovie,
            newItem: FavoriteMovie
        ): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun onBindViewHolder(holder: ReminderRecyclerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClicked(item.id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderRecyclerViewHolder {
        return ReminderRecyclerViewHolder(
            ItemFavoriteMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}