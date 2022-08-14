package ua.mrrobot1413.movies.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ua.mrrobot1413.movies.data.storage.model.TopMovie

@Dao
interface TopMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoviesList(list: List<TopMovie>)

    @Query("SELECT * FROM topMoviesTable ORDER BY position ASC")
    fun getMoviesListTable(): Flow<List<TopMovie>>

    @Query("DELETE FROM topMoviesTable")
    suspend fun deleteMovies()
}