package ua.mrrobot1413.movies.ui.home.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ua.mrrobot1413.movies.data.network.model.MovieResponseModel
import ua.mrrobot1413.movies.databinding.ItemMovieBinding

class LatestRecyclerViewAdapter(
    private val onItemClicked: (Int) -> Unit
) :
    ListAdapter<MovieResponseModel, LatestRecyclerViewHolder>(object : DiffUtil.ItemCallback<MovieResponseModel>() {
        override fun areItemsTheSame(oldItem: MovieResponseModel, newItem: MovieResponseModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieResponseModel,
            newItem: MovieResponseModel
        ): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun onBindViewHolder(holder: LatestRecyclerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClicked(item.id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestRecyclerViewHolder {
        return LatestRecyclerViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}