package ua.mrrobot1413.movies.ui.viewAll

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ua.mrrobot1413.movies.data.network.model.MovieResponseModel
import ua.mrrobot1413.movies.databinding.ItemViewMovieBinding

class ViewAllRecyclerViewAdapter(
    private val onItemClicked: (Int) -> Unit
) :
    ListAdapter<MovieResponseModel, ViewAllRecyclerViewHolder>(object : DiffUtil.ItemCallback<MovieResponseModel>() {
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

    override fun onBindViewHolder(holder: ViewAllRecyclerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClicked(item.id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAllRecyclerViewHolder {
        return ViewAllRecyclerViewHolder(
            ItemViewMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}