package ua.mrrobot1413.movies.ui.search

import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import ua.mrrobot1413.movies.R
import ua.mrrobot1413.movies.base.BaseViewHolder
import ua.mrrobot1413.movies.data.network.model.MovieResponseModel
import ua.mrrobot1413.movies.databinding.ItemViewMovieBinding
import ua.mrrobot1413.movies.utils.Constants

class SearchRecyclerViewHolder(private val binding: ItemViewMovieBinding): BaseViewHolder<MovieResponseModel>(binding.root) {
    override fun bind(item: MovieResponseModel) {
        binding.run {
            txtAdult.isVisible = item.isAdult
            if(item.frontPoster != null) {
                Glide.with(root.context)
                    .load(Constants.IMG_URL + item.frontPoster)
                    .placeholder(R.drawable.background_placeholder)
                    .centerCrop()
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                    .into(img)
            }
        }
    }
}