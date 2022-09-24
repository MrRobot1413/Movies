package ua.mrrobot1413.movies.domain.useCase.detailed

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.data.network.model.DetailedMovie
import ua.mrrobot1413.movies.domain.DetailedRepository
import ua.mrrobot1413.movies.utils.Utils.roundTo
import java.math.BigInteger
import java.math.RoundingMode
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.roundToInt

class GetMovieDetailsUseCase @Inject constructor(
    private val detailedRepository: DetailedRepository
) {

    suspend fun invoke(id: Int): DetailedMovie {
        return withContext(Dispatchers.IO) {
        val response =  detailedRepository.getMovieDetails(id)
            response.rating = response.rating.roundTo(1).toFloat()
            response
        }
    }
}