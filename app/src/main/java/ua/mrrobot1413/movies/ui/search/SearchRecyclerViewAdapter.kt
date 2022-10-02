package ua.mrrobot1413.movies.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ua.mrrobot1413.movies.data.network.model.MovieResponseModel
import ua.mrrobot1413.movies.databinding.ItemViewMovieBinding

class SearchRecyclerViewAdapter(
    private val onItemClicked: (Int) -> Unit
) :
    PagingDataAdapter<MovieResponseModel, SearchRecyclerViewHolder>(object : DiffUtil.ItemCallback<MovieResponseModel>() {
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

    override fun onBindViewHolder(holder: SearchRecyclerViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
        holder.itemView.setOnClickListener {
            item?.id?.let { id -> onItemClicked(id) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRecyclerViewHolder {
        return SearchRecyclerViewHolder(
            ItemViewMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}