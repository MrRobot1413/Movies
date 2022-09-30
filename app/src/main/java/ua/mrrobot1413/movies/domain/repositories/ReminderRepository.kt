package ua.mrrobot1413.movies.domain.repositories

import kotlinx.coroutines.flow.Flow
import ua.mrrobot1413.movies.data.storage.model.ReminderMovie

interface ReminderRepository {

    suspend fun getReminderMovies(): Flow<List<ReminderMovie>>
    suspend fun isToRemindMovie(id: Int): Boolean
    suspend fun createReminder(movie: ReminderMovie)
    suspend fun deleteReminder(id: Int)
}