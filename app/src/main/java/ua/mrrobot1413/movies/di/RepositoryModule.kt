package ua.mrrobot1413.movies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.mrrobot1413.movies.data.network.Api
import ua.mrrobot1413.movies.data.repository.DetailedRepositoryImpl
import ua.mrrobot1413.movies.data.repository.FavoriteRepositoryImpl
import ua.mrrobot1413.movies.data.repository.HomeRepositoryImpl
import ua.mrrobot1413.movies.data.repository.ReminderRepositoryImpl
import ua.mrrobot1413.movies.data.storage.AppDatabase
import ua.mrrobot1413.movies.data.storage.dao.DetailedMoviesDao
import ua.mrrobot1413.movies.data.storage.dao.FavoriteMoviesDao
import ua.mrrobot1413.movies.data.storage.dao.ReminderMoviesDao
import ua.mrrobot1413.movies.domain.repositories.DetailedRepository
import ua.mrrobot1413.movies.domain.repositories.FavoriteRepository
import ua.mrrobot1413.movies.domain.repositories.HomeRepository
import ua.mrrobot1413.movies.domain.repositories.ReminderRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideHomeRepository(api: Api, appDatabase: AppDatabase): HomeRepository {
        return HomeRepositoryImpl(api, appDatabase)
    }

    @Provides
    @Singleton
    fun provideDetailedRepository(api: Api, dao: DetailedMoviesDao): DetailedRepository {
        return DetailedRepositoryImpl(api, dao)
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(dao: FavoriteMoviesDao): FavoriteRepository {
        return FavoriteRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideReminderRepository(dao: ReminderMoviesDao): ReminderRepository {
        return ReminderRepositoryImpl(dao)
    }
}