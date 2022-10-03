package ua.mrrobot1413.movies.domain.useCase.reminder

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.data.storage.model.ReminderMovie
import ua.mrrobot1413.movies.di.IoDispatcher
import ua.mrrobot1413.movies.domain.repositories.ReminderRepository
import javax.inject.Inject

class GetReminderMoviesUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun invoke(): Flow<List<ReminderMovie>> {
        return withContext(ioDispatcher) {
            reminderRepository.getReminderMovies()
        }
    }
}