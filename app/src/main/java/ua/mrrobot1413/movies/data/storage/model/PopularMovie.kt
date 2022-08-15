package ua.mrrobot1413.movies.data.storage.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "popularMoviesTable")
data class PopularMovie(
    val id: Int,
    val title: String,
    val isAdult: Boolean,
    val frontPoster: String?
) {
    @PrimaryKey(autoGenerate = true)
    var position: Int = 0
}

@Entity(tableName = "topMoviesTable")
data class TopMovie(
    val id: Int,
    val title: String,
    val isAdult: Boolean,
    val frontPoster: String?
) {
    @PrimaryKey(autoGenerate = true)
    var position: Int = 0
}

@Entity(tableName = "upcomingMoviesTable")
data class UpcomingMovie(
    val id: Int,
    val title: String,
    val isAdult: Boolean,
    val frontPoster: String?
) {
    @PrimaryKey(autoGenerate = true)
    var position: Int = 0
}