package ua.mrrobot1413.movies.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.data.network.Api
import ua.mrrobot1413.movies.data.storage.AppDatabase
import ua.mrrobot1413.movies.data.storage.model.TopMovie
import ua.mrrobot1413.movies.data.storage.model.TopMovieRemoteKeys

@OptIn(ExperimentalPagingApi::class)
class TopRatedMoviesRemoteMediator(
    private val api: Api,
    private val database: AppDatabase
) : RemoteMediator<Int, TopMovie>() {

    private val moviesDao = database.topMoviesDao()
    private val movieRemoteKeysDao = database.topMovieRemoteKeysDao()
    private var pages = 1

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TopMovie>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state = state)
                    remoteKeys?.nextKey?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstTime(state = state)
                    remoteKeys?.prevKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastTime(state = state)
                    remoteKeys?.nextKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                }
            }

            val response = api.getTopRatedMovies(page).results
            val endOfPaginationReached = response.isEmpty()

            val prevPage = null
            val nextPage = if (endOfPaginationReached) null else page + 1

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieRemoteKeysDao.clearRemoteKeys()
                    moviesDao.deleteMovies()
                }
                val keys = response.map { movie ->
                    TopMovieRemoteKeys(
                        movieId = movie.id.toLong(),
                        prevKey = prevPage,
                        nextKey = nextPage
                    )
                }
                movieRemoteKeysDao.insertAll(remoteKey = keys)
                moviesDao.insertMoviesList(list = response.map {
                    TopMovie(
                        it.id.toLong(),
                        it.title,
                        it.isAdult,
                        it.frontPoster
                    )
                })
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            println("Error: " + e.message)
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, TopMovie>
    ): TopMovieRemoteKeys? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.id?.let { id ->
                database.withTransaction {
                    movieRemoteKeysDao.remoteKeysByMovieId(movieId = id)
                }
            }
        }
    }

    private suspend fun getRemoteKeyForFirstTime(
        state: PagingState<Int, TopMovie>
    ): TopMovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let {
                database.withTransaction {
                    movieRemoteKeysDao.remoteKeysByMovieId(movieId = it.id)
                }
            }
    }

    private suspend fun getRemoteKeyForLastTime(
        state: PagingState<Int, TopMovie>
    ): TopMovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                database.withTransaction {
                    movieRemoteKeysDao.remoteKeysByMovieId(movieId = it.id)
                }
            }
    }
}