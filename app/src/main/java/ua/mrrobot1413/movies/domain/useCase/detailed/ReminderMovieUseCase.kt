package ua.mrrobot1413.movies.domain.useCase.detailed

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.domain.repositories.ReminderRepository
import javax.inject.Inject

class ReminderMovieUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository
) {

    suspend fun invoke(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            reminderRepository.isToRemindMovie(id)
        }
    }
}