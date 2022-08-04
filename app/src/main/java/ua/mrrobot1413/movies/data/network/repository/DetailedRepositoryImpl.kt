package ua.mrrobot1413.movies.data.network.repository

import ua.mrrobot1413.movies.data.network.Api
import ua.mrrobot1413.movies.data.network.model.DetailedMovie
import ua.mrrobot1413.movies.data.network.model.MoviesResponse
import ua.mrrobot1413.movies.domain.DetailedRepository
import java.math.BigInteger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailedRepositoryImpl @Inject constructor(
    private val api: Api
): DetailedRepository {
    override suspend fun getMovieDetails(id: Int): DetailedMovie {
        return api.getMovieDetails(id)
    }

    override suspend fun getSimilarMovies(id: Int): MoviesResponse {
        return api.getSimilarMovies(id)
    }

}