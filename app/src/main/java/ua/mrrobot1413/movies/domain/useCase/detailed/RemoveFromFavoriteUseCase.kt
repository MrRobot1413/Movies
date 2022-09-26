package ua.mrrobot1413.movies.domain.useCase.detailed

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.data.network.model.RequestType
import ua.mrrobot1413.movies.data.storage.model.DetailedMovie
import ua.mrrobot1413.movies.data.storage.model.FavoriteMovie
import ua.mrrobot1413.movies.data.storage.model.Genre
import ua.mrrobot1413.movies.domain.DetailedRepository
import ua.mrrobot1413.movies.domain.FavoriteRepository
import ua.mrrobot1413.movies.domain.HomeRepository
import javax.inject.Inject

class RemoveFromFavoriteUseCase @Inject constructor(
    private val detailedRepository: DetailedRepository,
    private val favoriteRepository: FavoriteRepository
) {

    suspend fun invoke(
        id: Int,
        detailedMovieId: Int
    ) {
        withContext(Dispatchers.IO) {
            detailedRepository.removeFromFavorite(detailedMovieId)
            favoriteRepository.removeFromFavorite(id)
        }
    }
}