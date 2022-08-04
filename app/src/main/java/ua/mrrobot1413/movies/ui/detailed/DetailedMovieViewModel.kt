package ua.mrrobot1413.movies.ui.detailed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.mrrobot1413.movies.data.network.model.DetailedMovie
import ua.mrrobot1413.movies.data.network.model.Movie
import ua.mrrobot1413.movies.data.network.model.Result
import ua.mrrobot1413.movies.domain.useCase.GetMovieDetailsUseCase
import ua.mrrobot1413.movies.domain.useCase.GetPopularMoviesUseCase
import ua.mrrobot1413.movies.domain.useCase.GetSimilarMoviesUseCase
import java.math.BigInteger
import javax.inject.Inject

@HiltViewModel
class DetailedMovieViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase
): ViewModel() {

    private val _details = MutableLiveData<Result<DetailedMovie>>()
    val details: LiveData<Result<DetailedMovie>> = _details

    private val _similarMovies = MutableLiveData<Result<PagingData<Movie>?>?>()
    val similarMovies: LiveData<Result<PagingData<Movie>?>?> = _similarMovies

    fun getMovieDetails(id: Int) {
        viewModelScope.launch {
            _details.value = Result.loading(null)
            var data: DetailedMovie? = null
            try {
                data = getMovieDetailsUseCase.invoke(id)
                _details.value = Result.success(data)
            } catch (e: Exception) {
                _details.value = Result.error(data, e.message)
            }
        }
    }
}