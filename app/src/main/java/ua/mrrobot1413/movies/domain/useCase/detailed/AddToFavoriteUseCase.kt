package ua.mrrobot1413.movies.domain.useCase.detailed

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.data.storage.model.DetailedMovie
import ua.mrrobot1413.movies.data.storage.model.FavoriteMovie
import ua.mrrobot1413.movies.data.storage.model.Genre
import ua.mrrobot1413.movies.domain.repositories.DetailedRepository
import ua.mrrobot1413.movies.domain.repositories.FavoriteRepository
import ua.mrrobot1413.movies.domain.repositories.HomeRepository
import javax.inject.Inject

class AddToFavoriteUseCase @Inject constructor(
    private val detailedRepository: DetailedRepository,
    private val favoriteRepository: FavoriteRepository,
    private val homeRepository: HomeRepository
) {

    suspend fun invoke(
        id: Int,
        detailedMovie: ua.mrrobot1413.movies.data.network.model.DetailedMovie
    ) {
        withContext(Dispatchers.IO) {
            val movie = homeRepository.getMovie(id)
            detailedRepository.addToFavorite(
                DetailedMovie(
                    id = detailedMovie.id,
                    backgroundPoster = detailedMovie.backgroundPoster,
                    budget = detailedMovie.budget,
                    genres = detailedMovie.genres.map {
                        Genre(it.name)
                    },
                    originalLanguage = detailedMovie.originalLanguage,
                    originalTitle = detailedMovie.originalTitle,
                    title = detailedMovie.title,
                    overview = detailedMovie.overview,
                    rating = detailedMovie.rating,
                    releaseDate = detailedMovie.releaseDate,
                    runtime = detailedMovie.runtime,
                    status = detailedMovie.status
                )
            )
            favoriteRepository.addToFavorite(
                FavoriteMovie(
                    movie.id,
                    movie.title,
                    movie.isAdult,
                    movie.frontPoster
                )
            )
        }
    }
}