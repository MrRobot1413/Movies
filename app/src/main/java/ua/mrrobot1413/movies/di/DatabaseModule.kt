package ua.mrrobot1413.movies.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.mrrobot1413.movies.data.storage.AppDatabase
import ua.mrrobot1413.movies.data.storage.dao.*
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
    fun providePopularMoviesDao(appDatabase: AppDatabase): MoviesDao {
        return appDatabase.moviesDao()
    }

    @Singleton
    @Provides
    fun provideFavoriteMoviesDao(appDatabase: AppDatabase): FavoriteMoviesDao {
        return appDatabase.favoriteMoviesDao()
    }

    @Singleton
    @Provides
    fun provideReminderMoviesDao(appDatabase: AppDatabase): ReminderMoviesDao {
        return appDatabase.reminderMoviesDao()
    }

    @Singleton
    @Provides
    fun provideDetailedMoviesDao(appDatabase: AppDatabase): DetailedMoviesDao {
        return appDatabase.detailedMoviesDao()
    }
}