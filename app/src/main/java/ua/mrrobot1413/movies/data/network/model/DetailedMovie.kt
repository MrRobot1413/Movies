package ua.mrrobot1413.movies.data.network.model

import com.google.gson.annotations.SerializedName

data class DetailedMovie(
    @SerializedName("backdrop_path")
    val backgroundPoster: String,
    @SerializedName("budget")
    val budget: Int,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("vote_average")
    val rating: Float,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("status")
    val status: String
)

data class Genre(
    @SerializedName("name")
    val name: String
)