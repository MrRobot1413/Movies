package ua.mrrobot1413.movies.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ua.mrrobot1413.movies.data.storage.model.TopMovieRemoteKeys

@Dao
interface TopMovieRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<TopMovieRemoteKeys>)

    @Query("SELECT * FROM TopMovieRemoteKeys WHERE movieId = :movieId")
    fun remoteKeysByMovieId(movieId: Long): TopMovieRemoteKeys?

    @Query("DELETE FROM TopMovieRemoteKeys")
    fun clearRemoteKeys()
}