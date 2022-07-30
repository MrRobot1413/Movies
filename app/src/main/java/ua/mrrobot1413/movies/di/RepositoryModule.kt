package ua.mrrobot1413.movies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.mrrobot1413.movies.data.network.Api
import ua.mrrobot1413.movies.data.network.repository.HomeRepositoryImpl
import ua.mrrobot1413.movies.domain.HomeRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideHomeRepository(api: Api): HomeRepository {
        return HomeRepositoryImpl(api)
    }
}