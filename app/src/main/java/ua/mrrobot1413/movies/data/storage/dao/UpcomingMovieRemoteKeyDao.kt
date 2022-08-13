package ua.mrrobot1413.movies.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ua.mrrobot1413.movies.data.storage.model.UpcomingMovieRemoteKeys

@Dao
interface UpcomingMovieRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<UpcomingMovieRemoteKeys>)

    @Query("SELECT * FROM UpcomingMovieRemoteKeys WHERE movieId = :movieId")
    fun remoteKeysByMovieId(movieId: Long): UpcomingMovieRemoteKeys?

    @Query("DELETE FROM UpcomingMovieRemoteKeys")
    fun clearRemoteKeys()
}