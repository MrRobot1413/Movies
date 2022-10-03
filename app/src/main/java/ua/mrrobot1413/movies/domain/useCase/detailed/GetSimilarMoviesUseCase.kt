package ua.mrrobot1413.movies.domain.useCase.detailed

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.data.network.model.MoviesResponse
import ua.mrrobot1413.movies.di.IoDispatcher
import ua.mrrobot1413.movies.domain.repositories.DetailedRepository
import javax.inject.Inject

class GetSimilarMoviesUseCase @Inject constructor(
    private val detailedRepository: DetailedRepository,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun invoke(id: Int): MoviesResponse {
        return withContext(ioDispatcher) { detailedRepository.getSimilarMovies(id) }
    }
}