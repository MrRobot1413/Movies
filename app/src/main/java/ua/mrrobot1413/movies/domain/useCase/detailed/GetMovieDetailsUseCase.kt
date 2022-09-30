package ua.mrrobot1413.movies.domain.useCase.detailed

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.data.network.model.DetailedMovie
import ua.mrrobot1413.movies.domain.repositories.DetailedRepository
import ua.mrrobot1413.movies.utils.Utils.roundTo
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val detailedRepository: DetailedRepository
) {

    suspend fun invoke(id: Int): DetailedMovie {
        return withContext(Dispatchers.IO) {
            val response = try {
                detailedRepository.getMovieDetails(id)
            } catch (e: Exception) {
                detailedRepository.getSavedMovieDetails(id)
            }
            response.rating = response.rating.roundTo(1).toFloat()
            response
        }
    }
}