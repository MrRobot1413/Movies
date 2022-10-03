package ua.mrrobot1413.movies.data.repository

import ua.mrrobot1413.movies.data.network.Api
import ua.mrrobot1413.movies.data.network.model.DetailedMovie
import ua.mrrobot1413.movies.data.network.model.Genre
import ua.mrrobot1413.movies.data.network.model.MoviesResponse
import ua.mrrobot1413.movies.data.storage.AppDatabase
import ua.mrrobot1413.movies.data.storage.dao.DetailedMoviesDao
import ua.mrrobot1413.movies.domain.repositories.DetailedRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailedRepositoryImpl @Inject constructor(
    private val api: Api,
    private val dao: DetailedMoviesDao
) : DetailedRepository {

    override suspend fun getMovieDetails(id: Int): DetailedMovie {
        return api.getMovieDetails(id)
    }

    override suspend fun getSavedMovieDetails(id: Int): DetailedMovie {
        val movie = dao.getMovie(id)
        return DetailedMovie(
            movie.id,
            movie.backgroundPoster,
            movie.budget,
            movie.genres.map {
                Genre(it.name)
            },
            movie.originalLanguage,
            movie.originalTitle,
            movie.title,
            movie.overview,
            movie.rating,
            movie.releaseDate,
            movie.runtime,
            movie.status
        )
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