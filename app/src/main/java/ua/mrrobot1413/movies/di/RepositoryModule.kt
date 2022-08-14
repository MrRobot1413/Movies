package ua.mrrobot1413.movies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.mrrobot1413.movies.data.network.Api
import ua.mrrobot1413.movies.data.repository.DetailedRepositoryImpl
import ua.mrrobot1413.movies.data.repository.HomeRepositoryImpl
import ua.mrrobot1413.movies.data.storage.AppDatabase
import ua.mrrobot1413.movies.data.storage.dao.PopularMoviesDao
import ua.mrrobot1413.movies.domain.DetailedRepository
import ua.mrrobot1413.movies.domain.HomeRepository
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
    fun provideDetailedRepository(api: Api): DetailedRepository {
        return DetailedRepositoryImpl(api)
    }
}