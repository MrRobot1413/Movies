package ua.mrrobot1413.movies.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ua.mrrobot1413.movies.base.BaseViewHolder
import ua.mrrobot1413.movies.data.network.model.Movie
import ua.mrrobot1413.movies.databinding.ItemViewMovieBinding

class SearchRecyclerViewAdapter(
    private val onItemClicked: (Int) -> Unit
) :
    PagingDataAdapter<Movie, SearchRecyclerViewHolder>(object : DiffUtil.ItemCallback<Movie>() {
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