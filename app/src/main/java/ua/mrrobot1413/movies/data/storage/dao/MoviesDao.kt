package ua.mrrobot1413.movies.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ua.mrrobot1413.movies.data.network.model.RequestType
import ua.mrrobot1413.movies.data.storage.model.Movie

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoviesList(list: List<Movie>)

    @Query("SELECT * FROM moviesTable WHERE movieType=:movieType ORDER BY position ASC")
    fun getMoviesListTable(movieType: RequestType): Flow<List<Movie>>

    @Query("SELECT * FROM moviesTable WHERE id=:id")
    suspend fun getMovie(id: Int): Movie
}