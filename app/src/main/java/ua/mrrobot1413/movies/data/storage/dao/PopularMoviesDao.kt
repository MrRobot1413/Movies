package ua.mrrobot1413.movies.data.storage.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ua.mrrobot1413.movies.data.storage.model.PopularMovie

@Dao
interface PopularMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoviesList(list: List<PopularMovie>)

    @Query("SELECT * FROM popularMoviesTable")
    fun getMoviesListTable(): Flow<List<PopularMovie>>

    @Query("DELETE FROM popularMoviesTable")
    suspend fun deleteMovies()
}