package ua.mrrobot1413.movies.domain.useCase.favorite

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.data.storage.model.FavoriteMovie
import ua.mrrobot1413.movies.domain.repositories.FavoriteRepository
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend fun invoke(): Flow<List<FavoriteMovie>> {
        return withContext(Dispatchers.IO) {
            favoriteRepository.getFavoriteMovies()
        }
    }
}