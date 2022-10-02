package ua.mrrobot1413.movies.data.repository

import kotlinx.coroutines.flow.Flow
import ua.mrrobot1413.movies.data.storage.AppDatabase
import ua.mrrobot1413.movies.data.storage.model.ReminderMovie
import ua.mrrobot1413.movies.domain.repositories.ReminderRepository
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : ReminderRepository {
    private val dao = appDatabase.reminderMoviesDao()

    override suspend fun getReminderMovies(): Flow<List<ReminderMovie>> {
        return dao.getReminderMovies()
    }

    override suspend fun isToRemindMovie(id: Int): Boolean {
        return dao.isToRemind(id) != 0
    }

    override suspend fun createReminder(movie: ReminderMovie) {
        dao.insertMovie(movie)
    }

    override suspend fun deleteReminder(id: Int) {
        dao.removeMovie(id)
    }
}