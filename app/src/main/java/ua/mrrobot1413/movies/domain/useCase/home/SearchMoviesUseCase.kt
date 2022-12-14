package ua.mrrobot1413.movies.domain.useCase.home

import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.data.network.model.MovieResponseModel
import ua.mrrobot1413.movies.di.IoDispatcher
import ua.mrrobot1413.movies.domain.repositories.HomeRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun invoke(query: String): Flow<PagingData<MovieResponseModel>> {
        return withContext(ioDispatcher) {
            homeRepository.searchMovies(query)
        }
    }
}