package ua.mrrobot1413.movies.domain.repositories

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ua.mrrobot1413.movies.data.network.model.MovieResponseModel
import ua.mrrobot1413.movies.data.network.model.Result
import ua.mrrobot1413.movies.data.storage.model.Movie

interface HomeRepository {

    suspend fun getPopularMovies(page: Int): Flow<Result<List<Movie>>>
    suspend fun getTopRatedMovies(page: Int): Flow<Result<List<Movie>>>
    suspend fun getUpcomingMovies(page: Int): Flow<Result<List<Movie>>>
    suspend fun getMovie(id: Int): Movie
    suspend fun searchMovies(query: String): Flow<PagingData<MovieResponseModel>>
}