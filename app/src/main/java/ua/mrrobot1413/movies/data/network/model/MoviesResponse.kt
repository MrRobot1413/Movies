package ua.mrrobot1413.movies.data.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class MoviesResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieResponseModel>
)

@Parcelize
data class MovieResponseModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("adult")
    val isAdult: Boolean,
    @SerializedName("poster_path")
    val frontPoster: String?
): Parcelable