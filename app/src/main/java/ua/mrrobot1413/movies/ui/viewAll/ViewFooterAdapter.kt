package ua.mrrobot1413.movies.ui.viewAll

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import ua.mrrobot1413.movies.databinding.ItemLoadFooterBinding
import ua.mrrobot1413.movies.databinding.ItemViewLoadFooterBinding

class ViewFooterAdapter : LoadStateAdapter<ViewFooterViewHolder>() {
    override fun onBindViewHolder(holder: ViewFooterViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ViewFooterViewHolder {
        return ViewFooterViewHolder(
            ItemViewLoadFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

}