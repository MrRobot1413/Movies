package ua.mrrobot1413.movies.domain.useCase.detailed

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.data.network.model.DetailedMovie
import ua.mrrobot1413.movies.data.storage.model.Genre
import ua.mrrobot1413.movies.data.storage.model.ReminderMovie
import ua.mrrobot1413.movies.domain.repositories.DetailedRepository
import ua.mrrobot1413.movies.domain.repositories.HomeRepository
import ua.mrrobot1413.movies.domain.repositories.ReminderRepository
import javax.inject.Inject

class CreateReminderMovieUseCase @Inject constructor(
    private val detailedRepository: DetailedRepository,
    private val reminderRepository: ReminderRepository,
    private val homeRepository: HomeRepository
) {

    suspend fun invoke(id: Int, detailedMovie: DetailedMovie) {
        withContext(Dispatchers.IO) {
            val movie = homeRepository.getMovie(id)
            detailedRepository.addToFavorite(
                ua.mrrobot1413.movies.data.storage.model.DetailedMovie(
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
            reminderRepository.createReminder(
                ReminderMovie(
                    movie.id,
                    movie.title,
                    movie.isAdult,
                    movie.frontPoster
                )
            )
        }
    }
}