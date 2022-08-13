package ua.mrrobot1413.movies.data.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import ua.mrrobot1413.movies.utils.GenreConverter

@Entity(tableName = "detailedMoviesTable")
data class DetailedMovie(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val backgroundPoster: String,
    val budget: Int,
    @TypeConverters(GenreConverter::class)
    val genres: List<Genre>,
    val originalLanguage: String,
    val originalTitle: String,
    val title: String,
    val overview: String,
    var rating: Float,
    val releaseDate: String,
    val runtime: Int,
    val status: String
)

data class Genre(
    @SerializedName("name")
    val name: String
)