package ua.mrrobot1413.movies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import ua.mrrobot1413.movies.data.network.Api
import ua.mrrobot1413.movies.data.network.model.MovieResponseModel
import ua.mrrobot1413.movies.data.network.model.RequestType
import ua.mrrobot1413.movies.data.paging.SearchMoviesPagingSource
import ua.mrrobot1413.movies.data.storage.AppDatabase
import ua.mrrobot1413.movies.data.storage.model.Movie
import ua.mrrobot1413.movies.domain.HomeRepository
import ua.mrrobot1413.movies.utils.networkBoundResource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepositoryImpl @Inject constructor(
    private val api: Api,
    private val database: AppDatabase
) : HomeRepository {

    private val moviesDao = database.moviesDao()

    override suspend fun getPopularMovies(page: Int) = networkBoundResource(
        query = {
            moviesDao.getMoviesListTable(RequestType.POPULAR)
        },
        fetch = {
            api.getPopularMovies(page).results.mapIndexed { index, movie ->
                Movie(
                    movie.id,
                    index * page,
                    movie.title,
                    movie.isAdult,
                    movie.frontPoster,
                    movieType = RequestType.POPULAR
                )
            }
        },
        saveFetchResult = { movies ->
            database.withTransaction {
                moviesDao.insertMoviesList(movies)
            }
        }
    )

    override suspend fun getTopRatedMovies(page: Int) = networkBoundResource(
        query = {
            moviesDao.getMoviesListTable(RequestType.TOP_RATED)
        },
        fetch = {
            api.getTopRatedMovies(page).results.mapIndexed { index, movie ->
                Movie(
                    movie.id,
                    index * page,
                    movie.title,
                    movie.isAdult,
                    movie.frontPoster,
                    movieType = RequestType.TOP_RATED
                )
            }
        },
        saveFetchResult = { movies ->
            database.withTransaction {
                moviesDao.insertMoviesList(movies)
            }
        }
    )

    override suspend fun getUpcomingMovies(page: Int) = networkBoundResource(
        query = {
            moviesDao.getMoviesListTable(RequestType.UPCOMING)
        },
        fetch = {
            api.getUpcomingMovies(page).results.mapIndexed { index, movie ->
                Movie(
                    movie.id,
                    index * page,
                    movie.title,
                    movie.isAdult,
                    movie.frontPoster,
                    movieType = RequestType.UPCOMING
                )
            }
        },
        saveFetchResult = { movies ->
            database.withTransaction {
                moviesDao.insertMoviesList(movies)
            }
        }
    )

    override suspend fun getMovie(id: Int): Movie {
        return database.moviesDao().getMovie(id)
    }

    override suspend fun searchMovies(query: String): Flow<PagingData<MovieResponseModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 50
            ),
            pagingSourceFactory = { SearchMoviesPagingSource(api, query) }
        ).flow
    }
}