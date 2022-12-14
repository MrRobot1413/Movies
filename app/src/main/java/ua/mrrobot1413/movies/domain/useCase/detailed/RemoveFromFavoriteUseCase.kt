package ua.mrrobot1413.movies.domain.useCase.detailed

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.di.IoDispatcher
import ua.mrrobot1413.movies.domain.repositories.DetailedRepository
import ua.mrrobot1413.movies.domain.repositories.FavoriteRepository
import ua.mrrobot1413.movies.domain.repositories.ReminderRepository
import javax.inject.Inject

class RemoveFromFavoriteUseCase @Inject constructor(
    private val detailedRepository: DetailedRepository,
    private val favoriteRepository: FavoriteRepository,
    private val reminderRepository: ReminderRepository,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun invoke(
        id: Int,
        detailedMovieId: Int
    ) {
        withContext(ioDispatcher) {
            detailedRepository.removeFromFavorite(detailedMovieId)
            if(!reminderRepository.isToRemindMovie(id)) {
                favoriteRepository.removeFromFavorite(id)
            }
        }
    }
}