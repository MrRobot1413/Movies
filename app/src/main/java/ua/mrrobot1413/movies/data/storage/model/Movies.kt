package ua.mrrobot1413.movies.data.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.mrrobot1413.movies.data.network.model.RequestType

@Entity(tableName = "moviesTable")
data class Movie(
    @PrimaryKey
    val id: Int,
    val position: Int,
    val title: String,
    val isAdult: Boolean,
    val frontPoster: String?,
    val isFavorite: Boolean = false,
    val movieType: RequestType
)