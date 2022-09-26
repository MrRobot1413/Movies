package ua.mrrobot1413.movies.domain

import kotlinx.coroutines.flow.Flow
import ua.mrrobot1413.movies.data.storage.model.FavoriteMovie

interface FavoriteRepository {

    suspend fun getFavoriteMovies(): Flow<List<FavoriteMovie>>
    suspend fun isFavoriteMovie(id: Int): Boolean
    suspend fun addToFavorite(movie: FavoriteMovie)
    suspend fun removeFromFavorite(id: Int)
}