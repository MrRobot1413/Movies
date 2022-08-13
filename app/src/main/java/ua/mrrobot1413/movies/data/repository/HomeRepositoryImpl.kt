package ua.mrrobot1413.movies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ua.mrrobot1413.movies.data.network.Api
import ua.mrrobot1413.movies.data.network.model.Movie
import ua.mrrobot1413.movies.data.network.model.MoviesResponse
import ua.mrrobot1413.movies.data.paging.SearchMoviesPagingSource
import ua.mrrobot1413.movies.data.storage.AppDatabase
import ua.mrrobot1413.movies.domain.HomeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepositoryImpl @Inject constructor(
    private val api: Api,
    private val database: AppDatabase
) : HomeRepository {

    override suspend fun getPopularMovies(page: Int): MoviesResponse {
        return api.getPopularMovies(page)
    }

    override suspend fun getTopRatedMovies(page: Int): MoviesResponse {
        return api.getTopRatedMovies(page)
    }

    override suspend fun getUpcomingMovies(page: Int): MoviesResponse {
        return api.getUpcomingMovies(page)
    }

    override suspend fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 50
            ),
            pagingSourceFactory = { SearchMoviesPagingSource(api, query) }
        ).flow
    }
}