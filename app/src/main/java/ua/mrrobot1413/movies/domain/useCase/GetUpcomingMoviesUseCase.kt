package ua.mrrobot1413.movies.domain.useCase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.data.network.model.Movie
import ua.mrrobot1413.movies.data.network.model.MoviesResponse
import ua.mrrobot1413.movies.domain.HomeRepository
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {

    suspend fun invoke(page: Int): Flow<List<Movie>?> {
        return withContext(Dispatchers.IO) {
            homeRepository.getUpcomingMovies(page).map {
                it.data?.map { movie ->
                    Movie(
                        movie.id,
                        movie.title,
                        movie.isAdult,
                        movie.frontPoster
                    )
                }
            }
        }
    }
}