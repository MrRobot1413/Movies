package ua.mrrobot1413.movies.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ua.mrrobot1413.movies.data.storage.model.DetailedMovie

@Dao
interface DetailedMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(detailedMovie: DetailedMovie)

    @Query("DELETE FROM detailedMoviesTable WHERE id=:id")
    suspend fun removeMovie(id: Int)

    @Query("SELECT * FROM detailedMoviesTable WHERE id=:id")
    suspend fun getMovie(id: Int): DetailedMovie
}