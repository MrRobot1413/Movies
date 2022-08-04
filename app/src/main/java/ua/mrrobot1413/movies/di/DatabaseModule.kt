package ua.mrrobot1413.movies.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.mrrobot1413.movies.data.storage.AppDatabase
import ua.mrrobot1413.movies.data.storage.dao.*
import ua.mrrobot1413.movies.data.storage.model.PopularMovieRemoteKeys
import ua.mrrobot1413.movies.utils.Constants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            Constants.DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun providePopularMoviesDao(appDatabase: AppDatabase): PopularMoviesDao {
        return appDatabase.popularMoviesDao()
    }

    @Singleton
    @Provides
    fun provideTopMoviesDao(appDatabase: AppDatabase): TopMoviesDao {
        return appDatabase.topMoviesDao()
    }

    @Singleton
    @Provides
    fun provideUpcomingMoviesDao(appDatabase: AppDatabase): UpcomingMoviesDao {
        return appDatabase.upcomingMoviesDao()
    }

    @Singleton
    @Provides
    fun providePopularMovieRemoteKeysDao(appDatabase: AppDatabase): PopularMovieRemoteKeyDao {
        return appDatabase.popularMovieRemoteKeysDao()
    }

    @Singleton
    @Provides
    fun provideTopMovieRemoteKeysDao(appDatabase: AppDatabase): TopMovieRemoteKeyDao {
        return appDatabase.topMovieRemoteKeysDao()
    }

    @Singleton
    @Provides
    fun provideUpcomingMovieRemoteKeysDao(appDatabase: AppDatabase): UpcomingMovieRemoteKeyDao {
        return appDatabase.upcomingMovieRemoteKeysDao()
    }
}