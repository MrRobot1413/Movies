package ua.mrrobot1413.movies.data.network.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ua.mrrobot1413.movies.data.network.Api
import ua.mrrobot1413.movies.data.network.model.Movie
import ua.mrrobot1413.movies.data.network.model.RequestType
import ua.mrrobot1413.movies.data.network.paging.PopularMoviesPagingSource
import ua.mrrobot1413.movies.data.network.paging.TopRatedMoviesPagingSource
import ua.mrrobot1413.movies.data.network.paging.UpcomingMoviesPagingSource
import ua.mrrobot1413.movies.domain.HomeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepositoryImpl @Inject constructor(private val api: Api) : HomeRepository {

    override suspend fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25
            ),
            pagingSourceFactory = { PopularMoviesPagingSource(api) }
        ).flow
    }

    override suspend fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25
            ),
            pagingSourceFactory = { TopRatedMoviesPagingSource(api) }
        ).flow
    }

    override suspend fun getUpcomingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25
            ),
            pagingSourceFactory = { UpcomingMoviesPagingSource(api) }
        ).flow
    }
}