package ua.mrrobot1413.movies.domain.useCase.detailed

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.domain.repositories.FavoriteRepository
import javax.inject.Inject

class FavoriteMovieUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {

    suspend fun invoke(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            favoriteRepository.isFavoriteMovie(id)
        }
    }
}