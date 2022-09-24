package ua.mrrobot1413.movies.data.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteMoviesTable")
data class FavoriteMovie(
    @PrimaryKey
    val id: Int,
    val position: Int,
    val title: String,
    val isAdult: Boolean,
    val frontPoster: String?
)