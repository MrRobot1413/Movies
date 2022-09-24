package ua.mrrobot1413.movies.ui.viewAll

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.mrrobot1413.movies.data.network.model.MovieResponseModel
import ua.mrrobot1413.movies.data.network.model.RequestType
import ua.mrrobot1413.movies.data.network.model.Result
import ua.mrrobot1413.movies.domain.useCase.home.GetPopularMoviesUseCase
import ua.mrrobot1413.movies.domain.useCase.home.GetTopRatedMoviesUseCase
import ua.mrrobot1413.movies.domain.useCase.home.GetUpcomingMoviesUseCase
import javax.inject.Inject

@HiltViewModel
class ViewAllViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase
): ViewModel() {

    private val _movies = MutableLiveData<Result<List<MovieResponseModel>?>>()
    val movies: LiveData<Result<List<MovieResponseModel>?>> = _movies

    var pages = 1

    fun getMovies(requestType: RequestType, page: Int) {
        viewModelScope.launch {
            _movies.value = Result.loading(null)
            try {
                delay(600)
                val useCase = when(requestType) {
                    RequestType.POPULAR -> getPopularMoviesUseCase.invoke(page)
                    RequestType.TOP_RATED -> getTopRatedMoviesUseCase.invoke(page)
                    RequestType.UPCOMING -> getUpcomingMoviesUseCase.invoke(page)
                }
                useCase.collect {
                    _movies.value = Result.success(it)
                }
            } catch (e: Exception) {
                println("Ex 4: ${e.message}")
                _movies.value = Result.error(null, e.message)
            }
        }
    }
}