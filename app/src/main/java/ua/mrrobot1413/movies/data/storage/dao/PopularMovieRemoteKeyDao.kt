package ua.mrrobot1413.movies.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ua.mrrobot1413.movies.data.storage.model.PopularMovieRemoteKeys

@Dao
interface PopularMovieRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<PopularMovieRemoteKeys>)

    @Query("SELECT * FROM PopularMovieRemoteKeys WHERE movieId = :movieId")
    fun remoteKeysByMovieId(movieId: Long): PopularMovieRemoteKeys?

    @Query("DELETE FROM PopularMovieRemoteKeys")
    fun clearRemoteKeys()
}