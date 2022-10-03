package ua.mrrobot1413.movies.domain.useCase.home

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.data.network.model.MovieResponseModel
import ua.mrrobot1413.movies.di.IoDispatcher
import ua.mrrobot1413.movies.domain.repositories.HomeRepository
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun invoke(page: Int): Flow<List<MovieResponseModel>?> {
        return withContext(ioDispatcher) {
            homeRepository.getTopRatedMovies(page).map {
                it.data?.map { movie ->
                    MovieResponseModel(
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