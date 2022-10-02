package ua.mrrobot1413.movies.data.repository

import kotlinx.coroutines.flow.Flow
import ua.mrrobot1413.movies.data.storage.AppDatabase
import ua.mrrobot1413.movies.data.storage.model.FavoriteMovie
import ua.mrrobot1413.movies.domain.repositories.FavoriteRepository
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : FavoriteRepository {

    private val dao by lazy {
        appDatabase.favoriteMoviesDao()
    }

    override suspend fun getFavoriteMovies(): Flow<List<FavoriteMovie>> {
        return dao.getFavoriteMovies()
    }

    override suspend fun isFavoriteMovie(id: Int): Boolean {
        return dao.isFavorite(id) == 0
    }

    override suspend fun addToFavorite(movie: FavoriteMovie) {
        dao.insertMovie(movie)
    }

    override suspend fun removeFromFavorite(id: Int) {
        dao.removeMovie(id)
    }
}