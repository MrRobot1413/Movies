package ua.mrrobot1413.movies.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEmpty
import ua.mrrobot1413.movies.data.network.model.Movie
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

    private val _popularMovies = MutableLiveData<Result<PagingData<Movie>?>?>()
    val popularMovies: LiveData<Result<PagingData<Movie>?>?> = _popularMovies

    private val _topRatedMovies = MutableLiveData<Result<PagingData<Movie>?>?>(null)
    val topRatedMovies: LiveData<Result<PagingData<Movie>?>?> = _topRatedMovies

    private val _upcomingMovies = MutableLiveData<Result<PagingData<Movie>?>?>(null)
    val upcomingMovies: LiveData<Result<PagingData<Movie>?>?> = _upcomingMovies

    private var popularMoviesJob: Job? = null
    private var topRatedMoviesJob: Job? = null

    fun getMovies() {
        getPopularMovies()
        getTopRatedMovies()
        getUpcomingMovies()
    }

    private fun getPopularMovies() {
        popularMoviesJob = viewModelScope.launch {
            _popularMovies.value = Result.loading(null)
            return@launch try {
                getPopularMoviesUseCase.invoke().cachedIn(viewModelScope).collect {
                    _popularMovies.value = Result.success(it)
                        this.cancel()
                }
            } catch (e: Exception) {
                println("Ex: ${e.message}")
                _popularMovies.value = Result.error(null, e.message)
            }
        }
    }

    private fun getTopRatedMovies() {
        topRatedMoviesJob = viewModelScope.launch {
            _topRatedMovies.value = Result.loading(null)
            return@launch try {
                getTopRatedMoviesUseCase.invoke().cachedIn(viewModelScope).collect {
                    _topRatedMovies.value = Result.success(it)
                    this.cancel()
                }
            } catch (e: Exception) {
                println("Ex: ${e.message}")
                _topRatedMovies.value = Result.error(null, e.message)
            }
        }
    }

    private fun getUpcomingMovies() {
        viewModelScope.launch {
            _upcomingMovies.value = Result.loading(null)
            try {
                getUpcomingMoviesUseCase.invoke().cachedIn(viewModelScope).onCompletion {
                    popularMoviesJob?.join()
                    topRatedMoviesJob?.join()
                }.collect {
                    popularMoviesJob?.join()
                    topRatedMoviesJob?.join()
                    _upcomingMovies.value = Result.success(it)
                }

            } catch (e: Exception) {
                println("Ex: ${e.message}")
                _topRatedMovies.value = Result.error(null, e.message)
            }
        }
    }
}