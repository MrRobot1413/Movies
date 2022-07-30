package ua.mrrobot1413.movies.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ua.mrrobot1413.movies.data.network.model.Movie

interface HomeRepository {

    suspend fun getPopularMovies(): Flow<PagingData<Movie>>
    suspend fun getTopRatedMovies(): Flow<PagingData<Movie>>
    suspend fun getUpcomingMovies(): Flow<PagingData<Movie>>
}