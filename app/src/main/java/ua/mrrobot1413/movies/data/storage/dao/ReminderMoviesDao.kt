package ua.mrrobot1413.movies.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ua.mrrobot1413.movies.data.storage.model.FavoriteMovie
import ua.mrrobot1413.movies.data.storage.model.ReminderMovie

@Dao
interface ReminderMoviesDao {

    @Query("SELECT * FROM reminderMoviesTable")
    fun getReminderMovies(): Flow<List<ReminderMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: ReminderMovie)

    @Query("DELETE FROM reminderMoviesTable WHERE id=:id")
    suspend fun removeMovie(id: Int)

    @Query("SELECT COUNT(*) FROM reminderMoviesTable WHERE id=:id")
    suspend fun isToRemind(id: Int): Int
}