package ua.mrrobot1413.movies.data.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class RequestType: Parcelable {
    POPULAR,
    TOP_RATED,
    UPCOMING
}