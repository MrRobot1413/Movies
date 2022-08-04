package ua.mrrobot1413.movies.ui.viewAll

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.mrrobot1413.movies.data.network.model.Movie
import ua.mrrobot1413.movies.data.network.model.RequestType
import ua.mrrobot1413.movies.data.network.model.Result
import ua.mrrobot1413.movies.domain.useCase.GetPopularMoviesUseCase
import ua.mrrobot1413.movies.domain.useCase.GetTopRatedMoviesUseCase
import ua.mrrobot1413.movies.domain.useCase.GetUpcomingMoviesUseCase
import javax.inject.Inject

@HiltViewModel
class ViewAllViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase
): ViewModel() {

    private val _movies = MutableLiveData<Result<PagingData<Movie>?>?>()
    val movies: LiveData<Result<PagingData<Movie>?>?> = _movies

    fun getMovies(requestType: RequestType) {
        viewModelScope.launch {
            _movies.value = Result.loading(null)
            try {
                val useCase = when(requestType) {
                    RequestType.POPULAR -> getPopularMoviesUseCase.invoke()
                    RequestType.TOP_RATED -> getTopRatedMoviesUseCase.invoke()
                    RequestType.UPCOMING -> getUpcomingMoviesUseCase.invoke()
                }
                useCase?.cachedIn(viewModelScope)?.collect {
                    delay(600)
                    _movies.postValue(Result.success(it))
                }
            } catch (e: Exception) {
                println("Ex: ${e.message}")
                _movies.value = Result.error(null, e.message)
            }
        }
    }
}