package ua.mrrobot1413.movies.domain.useCase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.data.network.model.DetailedMovie
import ua.mrrobot1413.movies.domain.DetailedRepository
import java.math.BigInteger
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val detailedRepository: DetailedRepository
) {

    suspend fun invoke(id: Int): DetailedMovie {
        return withContext(Dispatchers.IO) { detailedRepository.getMovieDetails(id) }
    }
}