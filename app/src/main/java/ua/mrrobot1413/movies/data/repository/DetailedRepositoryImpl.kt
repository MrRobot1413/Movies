package ua.mrrobot1413.movies.data.repository

import ua.mrrobot1413.movies.data.network.Api
import ua.mrrobot1413.movies.data.network.model.DetailedMovie
import ua.mrrobot1413.movies.data.network.model.MoviesResponse
import ua.mrrobot1413.movies.data.network.model.RequestType
import ua.mrrobot1413.movies.data.storage.AppDatabase
import ua.mrrobot1413.movies.data.storage.model.FavoriteMovie
import ua.mrrobot1413.movies.domain.DetailedRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailedRepositoryImpl @Inject constructor(
    private val api: Api,
    private val appDatabase: AppDatabase
): DetailedRepository {

    private val dao = appDatabase.detailedMoviesDao()

    override suspend fun getMovieDetails(id: Int): DetailedMovie {
        return api.getMovieDetails(id)
    }

    override suspend fun getSimilarMovies(id: Int): MoviesResponse {
        return api.getSimilarMovies(id)
    }

    override suspend fun addToFavorite(detailedMovie: ua.mrrobot1413.movies.data.storage.model.DetailedMovie) {
        dao.insertMovie(detailedMovie)
    }

    override suspend fun removeFromFavorite(id: Int) {
        dao.removeMovie(id)
    }
}