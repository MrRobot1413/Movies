package ua.mrrobot1413.movies.domain.useCase.detailed

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.domain.repositories.DetailedRepository
import ua.mrrobot1413.movies.domain.repositories.FavoriteRepository
import ua.mrrobot1413.movies.domain.repositories.ReminderRepository
import javax.inject.Inject

class DeleteReminderUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository,
    private val favoriteRepository: FavoriteRepository,
    private val detailedRepository: DetailedRepository
) {

    suspend fun invoke(id: Int) {
        withContext(Dispatchers.IO) {
            reminderRepository.deleteReminder(id)
            if (!favoriteRepository.isFavoriteMovie(id)) {
                detailedRepository.removeFromFavorite(id)
            }
        }
    }
}