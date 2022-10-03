package ua.mrrobot1413.movies.domain.useCase.detailed

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.data.network.model.DetailedMovie
import ua.mrrobot1413.movies.di.IoDispatcher
import ua.mrrobot1413.movies.domain.repositories.DetailedRepository
import ua.mrrobot1413.movies.utils.Utils.roundTo
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val detailedRepository: DetailedRepository,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun invoke(id: Int): DetailedMovie {
        return withContext(ioDispatcher) {
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