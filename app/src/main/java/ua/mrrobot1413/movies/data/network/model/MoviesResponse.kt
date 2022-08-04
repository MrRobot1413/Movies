package ua.mrrobot1413.movies.data.network.model

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class MoviesResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Movie>
)

data class Movie(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("adult")
    val isAdult: Boolean,
    @SerializedName("poster_path")
    val frontPoster: String?,
    @SerializedName("backdrop_path")
    val backgroundPoster: String,
    @SerializedName("vote_average")
    val voteRating: Float
)