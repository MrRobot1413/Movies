package ua.mrrobot1413.movies.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ua.mrrobot1413.movies.data.storage.dao.*
import ua.mrrobot1413.movies.data.storage.model.*
import ua.mrrobot1413.movies.utils.GenreConverter
import ua.mrrobot1413.movies.utils.MoviesListConverter

@Database(
    entities = [
        Movie::class,
        DetailedMovie::class,
        FavoriteMovie::class,
        ReminderMovie::class],
    version = 20,
    exportSchema = false
)
@TypeConverters(MoviesListConverter::class, GenreConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
    abstract fun favoriteMoviesDao(): FavoriteMoviesDao
    abstract fun detailedMoviesDao(): DetailedMoviesDao
    abstract fun reminderMoviesDao(): ReminderMoviesDao
}