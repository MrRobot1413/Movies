package ua.mrrobot1413.movies.domain.useCase.detailed

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.data.network.model.RequestType
import ua.mrrobot1413.movies.domain.DetailedRepository
import javax.inject.Inject

class FavoriteMovieUseCase @Inject constructor(
    private val detailedRepository: DetailedRepository
) {

    suspend fun invoke(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            detailedRepository.isFavoriteMovie(id)
        }
    }
}