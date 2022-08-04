package ua.mrrobot1413.movies.domain

import ua.mrrobot1413.movies.data.network.model.DetailedMovie
import ua.mrrobot1413.movies.data.network.model.MoviesResponse
import java.math.BigInteger

interface DetailedRepository {

    suspend fun getMovieDetails(id: Int): DetailedMovie
    suspend fun getSimilarMovies(id: Int): MoviesResponse
}