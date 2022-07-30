package ua.mrrobot1413.movies.domain.useCase

import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.data.network.model.Movie
import ua.mrrobot1413.movies.domain.HomeRepository
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {

    suspend fun invoke(): Flow<PagingData<Movie>> {
        return withContext(Dispatchers.IO) { homeRepository.getTopRatedMovies() }
    }
}