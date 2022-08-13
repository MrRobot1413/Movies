package ua.mrrobot1413.movies.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import ua.mrrobot1413.movies.data.network.Api
import ua.mrrobot1413.movies.data.storage.AppDatabase
import ua.mrrobot1413.movies.data.storage.model.PopularMovie
import ua.mrrobot1413.movies.data.storage.model.PopularMovieRemoteKeys

@OptIn(ExperimentalPagingApi::class)
class PopularMoviesRemoteMediator(
    private val api: Api,
    private val database: AppDatabase
) : RemoteMediator<Int, PopularMovie>() {

    private val moviesDao = database.popularMoviesDao()
    private val movieRemoteKeysDao = database.popularMovieRemoteKeysDao()
    private var pages = 1


    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PopularMovie>
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
                    pages++
                }
            }

            val response = api.getPopularMovies(page).results
            val endOfPaginationReached = response.isEmpty()

            val prevPage = null
            val nextPage = if (endOfPaginationReached) null else page + 1

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieRemoteKeysDao.clearRemoteKeys()
                    moviesDao.deleteMovies()
                }
                val keys = response.map { movie ->
                    PopularMovieRemoteKeys(
                        movieId = movie.id.toLong(),
                        prevKey = prevPage,
                        nextKey = nextPage
                    )
                }
                movieRemoteKeysDao.insertAll(remoteKey = keys)
                moviesDao.insertMoviesList(list = response.map {
                    PopularMovie(
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
        state: PagingState<Int, PopularMovie>
    ): PopularMovieRemoteKeys? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.id?.let { id ->
                database.withTransaction {
                    movieRemoteKeysDao.remoteKeysByMovieId(movieId = id)
                }
            }
        }
    }

    private suspend fun getRemoteKeyForFirstTime(
        state: PagingState<Int, PopularMovie>
    ): PopularMovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let {
                database.withTransaction {
                    movieRemoteKeysDao.remoteKeysByMovieId(movieId = it.id)
                }
            }
    }

    private suspend fun getRemoteKeyForLastTime(
        state: PagingState<Int, PopularMovie>
    ): PopularMovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                database.withTransaction {
                    movieRemoteKeysDao.remoteKeysByMovieId(movieId = it.id)
                }
            }
    }
}