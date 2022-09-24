package ua.mrrobot1413.movies.domain

import ua.mrrobot1413.movies.data.network.model.DetailedMovie
import ua.mrrobot1413.movies.data.network.model.MoviesResponse
import ua.mrrobot1413.movies.data.network.model.RequestType
import ua.mrrobot1413.movies.data.storage.model.FavoriteMovie
import java.math.BigInteger

interface DetailedRepository {

    suspend fun getMovieDetails(id: Int): DetailedMovie
    suspend fun getSimilarMovies(id: Int): MoviesResponse
    suspend fun isFavoriteMovie(id: Int): Boolean
    suspend fun addToFavorite(movie: FavoriteMovie, detailedMovie: ua.mrrobot1413.movies.data.storage.model.DetailedMovie)
    suspend fun removeFromFavorite(id: Int, detailedMovieId: Int)
}