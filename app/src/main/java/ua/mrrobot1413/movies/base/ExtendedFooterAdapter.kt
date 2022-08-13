package ua.mrrobot1413.movies.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import ua.mrrobot1413.movies.databinding.ItemViewLoadFooterBinding

class ExtendedFooterAdapter : LoadStateAdapter<ExtendedFooterViewHolder>() {
    override fun onBindViewHolder(holder: ExtendedFooterViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ExtendedFooterViewHolder {
        return ExtendedFooterViewHolder(
            ItemViewLoadFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

}