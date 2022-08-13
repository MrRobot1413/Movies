package ua.mrrobot1413.movies.domain.useCase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.data.network.model.MoviesResponse
import ua.mrrobot1413.movies.domain.HomeRepository
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {

    suspend fun invoke(page: Int): MoviesResponse {
        return withContext(Dispatchers.IO) {
            homeRepository.getUpcomingMovies(page)
        }
    }
}