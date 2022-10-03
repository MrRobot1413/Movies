package ua.mrrobot1413.movies.domain.useCase.detailed

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.di.IoDispatcher
import ua.mrrobot1413.movies.domain.repositories.FavoriteRepository
import javax.inject.Inject

class FavoriteMovieUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun invoke(id: Int): Boolean {
        return withContext(ioDispatcher) {
            favoriteRepository.isFavoriteMovie(id)
        }
    }
}