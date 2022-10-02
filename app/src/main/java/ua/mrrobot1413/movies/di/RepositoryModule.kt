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
    fun provideDetailedRepository(api: Api, appDatabase: AppDatabase): DetailedRepository {
        return DetailedRepositoryImpl(api, appDatabase)
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(appDatabase: AppDatabase): FavoriteRepository {
        return FavoriteRepositoryImpl(appDatabase)
    }

    @Provides
    @Singleton
    fun provideReminderRepository(appDatabase: AppDatabase): ReminderRepository {
        return ReminderRepositoryImpl(appDatabase)
    }
}