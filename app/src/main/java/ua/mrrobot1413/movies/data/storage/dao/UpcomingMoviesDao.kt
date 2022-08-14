package ua.mrrobot1413.movies.data.storage.dao

import androidx.room.*
import ua.mrrobot1413.movies.data.storage.model.UpcomingMovie

@Dao
interface UpcomingMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoviesList(list: List<UpcomingMovie>)

    @Query("SELECT * FROM upcomingMoviesTable")
    fun getMoviesListTable(): PagingSource<Int, UpcomingMovie>

    @Query("DELETE FROM upcomingMoviesTable")
    suspend fun deleteMovies()
}