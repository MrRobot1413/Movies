package ua.mrrobot1413.movies.data.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PopularMovieRemoteKeys(
    @PrimaryKey
    val movieId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)

@Entity
data class TopMovieRemoteKeys(
    @PrimaryKey
    val movieId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)

@Entity
data class UpcomingMovieRemoteKeys(
    @PrimaryKey
    val movieId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)