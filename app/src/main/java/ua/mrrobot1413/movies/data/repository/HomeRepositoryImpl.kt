package ua.mrrobot1413.movies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.mrrobot1413.movies.data.network.Api
import ua.mrrobot1413.movies.data.network.model.Movie
import ua.mrrobot1413.movies.data.network.model.MoviesResponse
import ua.mrrobot1413.movies.data.paging.SearchMoviesPagingSource
import ua.mrrobot1413.movies.data.storage.AppDatabase
import ua.mrrobot1413.movies.data.storage.model.PopularMovie
import ua.mrrobot1413.movies.data.storage.model.TopMovie
import ua.mrrobot1413.movies.data.storage.model.UpcomingMovie
import ua.mrrobot1413.movies.domain.HomeRepository
import ua.mrrobot1413.movies.utils.networkBoundResource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepositoryImpl @Inject constructor(
    private val api: Api,
    private val database: AppDatabase
) : HomeRepository {

    private val popularDao = database.popularMoviesDao()
    private val topDao = database.topMoviesDao()
    private val upcomingDao = database.upcomingMoviesDao()

    override suspend fun getPopularMovies(page: Int) = networkBoundResource(
        query = {
            popularDao.getMoviesListTable()
        },
        fetch = {
            api.getPopularMovies(page).results.map { movie ->
                PopularMovie(
                    movie.id,
                    movie.title,
                    movie.isAdult,
                    movie.frontPoster
                )
            }
        },
        saveFetchResult = { movies ->
            database.withTransaction {
                popularDao.insertMoviesList(movies)
            }
        }
    )

    override suspend fun getTopRatedMovies(page: Int) = networkBoundResource(
        query = {
            topDao.getMoviesListTable()
        },
        fetch = {
            api.getTopRatedMovies(page).results.map { movie ->
                TopMovie(
                    movie.id,
                    movie.title,
                    movie.isAdult,
                    movie.frontPoster
                )
            }
        },
        saveFetchResult = { movies ->
            database.withTransaction {
                topDao.insertMoviesList(movies)
            }
        }
    )

    override suspend fun getUpcomingMovies(page: Int) = networkBoundResource(
        query = {
            upcomingDao.getMoviesListTable()
        },
        fetch = {
            api.getUpcomingMovies(page).results.map { movie ->
                UpcomingMovie(
                    movie.id,
                    movie.title,
                    movie.isAdult,
                    movie.frontPoster
                )
            }
        },
        saveFetchResult = { movies ->
            database.withTransaction {
                upcomingDao.insertMoviesList(movies)
            }
        }
    )

    override suspend fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 50
            ),
            pagingSourceFactory = { SearchMoviesPagingSource(api, query) }
        ).flow
    }
}