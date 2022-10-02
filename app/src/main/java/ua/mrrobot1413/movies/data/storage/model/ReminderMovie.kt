package ua.mrrobot1413.movies.data.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminderMoviesTable")
data class ReminderMovie(
    @PrimaryKey
    val id: Int,
    val title: String,
    val isAdult: Boolean,
    val frontPoster: String?
)