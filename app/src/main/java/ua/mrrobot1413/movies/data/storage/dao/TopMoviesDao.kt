package ua.mrrobot1413.movies.data.storage.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ua.mrrobot1413.movies.data.storage.model.TopMovie

@Dao
interface TopMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoviesList(list: List<TopMovie>)

    @Query("SELECT * FROM topMoviesTable")
    fun getMoviesListTable(): PagingSource<Int, TopMovie>

    @Query("DELETE FROM topMoviesTable")
    suspend fun deleteMovies()
}