package ua.mrrobot1413.movies.data.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "popularMoviesTable")
data class PopularMovie(
    @PrimaryKey
    val id: Int,
    val position: Int,
    val title: String,
    val isAdult: Boolean,
    val frontPoster: String?
)

@Entity(tableName = "topMoviesTable")
data class TopMovie(
    @PrimaryKey
    val id: Int,
    val position: Int,
    val title: String,
    val isAdult: Boolean,
    val frontPoster: String?
)

@Entity(tableName = "upcomingMoviesTable")
data class UpcomingMovie(
    @PrimaryKey
    val id: Int,
    val position: Int,
    val title: String,
    val isAdult: Boolean,
    val frontPoster: String?
)