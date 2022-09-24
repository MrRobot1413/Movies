package ua.mrrobot1413.movies.ui.viewAll

import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import ua.mrrobot1413.movies.R
import ua.mrrobot1413.movies.base.BaseViewHolder
import ua.mrrobot1413.movies.data.network.model.MovieResponseModel
import ua.mrrobot1413.movies.databinding.ItemViewMovieBinding
import ua.mrrobot1413.movies.utils.Constants

class ViewAllRecyclerViewHolder(private val binding: ItemViewMovieBinding): BaseViewHolder<MovieResponseModel>(binding.root) {
    override fun bind(item: MovieResponseModel) {
        binding.run {
            txtAdult.isVisible = item.isAdult
            Glide.with(root.context)
                .load(Constants.IMG_URL + item.frontPoster)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                .placeholder(R.drawable.background_placeholder)
                .into(img)
        }
    }
}