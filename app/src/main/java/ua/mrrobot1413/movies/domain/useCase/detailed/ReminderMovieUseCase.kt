package ua.mrrobot1413.movies.domain.useCase.detailed

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.di.IoDispatcher
import ua.mrrobot1413.movies.domain.repositories.ReminderRepository
import javax.inject.Inject

class ReminderMovieUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun invoke(id: Int): Boolean {
        return withContext(ioDispatcher) {
            reminderRepository.isToRemindMovie(id)
        }
    }
}