package ua.mrrobot1413.movies.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ua.mrrobot1413.movies.data.storage.model.FavoriteMovie

@Dao
interface FavoriteMoviesDao {

    @Query("SELECT * FROM favoriteMoviesTable")
    suspend fun getFavoriteMovies(): List<FavoriteMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: FavoriteMovie)

    @Query("DELETE FROM favoriteMoviesTable WHERE id=:id")
    suspend fun removeMovie(id: Int)

    @Query("SELECT COUNT(*) FROM favoriteMoviesTable WHERE id=:id")
    suspend fun isFavorite(id: Int): Int
}