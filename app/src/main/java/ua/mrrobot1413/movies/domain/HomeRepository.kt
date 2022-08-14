package ua.mrrobot1413.movies.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ua.mrrobot1413.movies.data.network.model.Movie
import ua.mrrobot1413.movies.data.network.model.MoviesResponse
import ua.mrrobot1413.movies.data.storage.model.PopularMovie
import ua.mrrobot1413.movies.data.storage.model.TopMovie
import ua.mrrobot1413.movies.data.storage.model.UpcomingMovie
import ua.mrrobot1413.movies.data.network.model.Result

interface HomeRepository {

    suspend fun getPopularMovies(page: Int): Flow<Result<List<PopularMovie>>>
    suspend fun getTopRatedMovies(page: Int): Flow<Result<List<TopMovie>>>
    suspend fun getUpcomingMovies(page: Int): Flow<Result<List<UpcomingMovie>>>
    suspend fun searchMovies(query: String): Flow<PagingData<Movie>>
}