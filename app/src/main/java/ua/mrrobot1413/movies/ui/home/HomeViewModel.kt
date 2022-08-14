package ua.mrrobot1413.movies.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import ua.mrrobot1413.movies.data.network.model.Movie
import ua.mrrobot1413.movies.data.network.model.MoviesResponse
import ua.mrrobot1413.movies.data.network.model.Result
import ua.mrrobot1413.movies.domain.useCase.GetPopularMoviesUseCase
import ua.mrrobot1413.movies.domain.useCase.GetTopRatedMoviesUseCase
import ua.mrrobot1413.movies.domain.useCase.GetUpcomingMoviesUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase
) : ViewModel() {

    private val _popularMovies = MutableLiveData<Result<List<Movie>?>>()
    val popularMovies: LiveData<Result<List<Movie>?>> = _popularMovies

    private val _topRatedMovies = MutableLiveData<Result<List<Movie>?>>(null)
    var topRatedMovies: LiveData<Result<List<Movie>?>> = _topRatedMovies

    private val _upcomingMovies = MutableLiveData<Result<List<Movie>?>>(null)
    val upcomingMovies: LiveData<Result<List<Movie>?>> = _upcomingMovies

    var popularPages = 1
    var topPages = 1
    var upcomingPages = 1

    fun getPopularMovies(page: Int) {
        viewModelScope.launch {
            _popularMovies.value = Result.loading(null)
            try {
                getPopularMoviesUseCase.invoke(page).collect {
                    _popularMovies.value = Result.success(it)
                }
            } catch (e: Exception) {
                println("Ex 1: ${e.message}")
                _popularMovies.value = Result.error(null, e.message)
            }
        }
    }

    fun getTopRatedMovies(page: Int) {
        viewModelScope.launch {
            _topRatedMovies.value = Result.loading(null)
            try {
                getTopRatedMoviesUseCase.invoke(page).collect {
                    _topRatedMovies.value = Result.success(it)
                }
            } catch (e: Exception) {
                println("Ex 2: ${e.message}")
                _topRatedMovies.value = Result.error(null, e.message)
            }
        }
    }

    fun getUpcomingMovies(page: Int) {
        viewModelScope.launch {
            _upcomingMovies.value = Result.loading(null)
            try {
                getUpcomingMoviesUseCase.invoke(page).collect {
                    _upcomingMovies.value = Result.success(it)
                }
            } catch (e: Exception) {
                println("Ex 3: ${e.message}")
                _topRatedMovies.value = Result.error(null, e.message)
            }
        }
    }
}