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
        PopularMovie::class,
        TopMovie::class,
        UpcomingMovie::class,
<<<<<<< HEAD
        DetailedMovie::class,
        PopularMovieRemoteKeys::class,
        TopMovieRemoteKeys::class,
        UpcomingMovieRemoteKeys::class],
    version = 11,
=======
        DetailedMovie::class],
    version = 15,
>>>>>>> 3798c1f2149e68f5556a3983d42f3f5b4c737063
    exportSchema = false
)
@TypeConverters(MoviesListConverter::class, GenreConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun popularMoviesDao(): PopularMoviesDao
    abstract fun topMoviesDao(): TopMoviesDao
    abstract fun upcomingMoviesDao(): UpcomingMoviesDao
<<<<<<< HEAD
    abstract fun popularMovieRemoteKeysDao(): PopularMovieRemoteKeyDao
    abstract fun topMovieRemoteKeysDao(): TopMovieRemoteKeyDao
    abstract fun upcomingMovieRemoteKeysDao(): UpcomingMovieRemoteKeyDao
=======
>>>>>>> 3798c1f2149e68f5556a3983d42f3f5b4c737063
}