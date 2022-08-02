package ua.mrrobot1413.movies.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ua.mrrobot1413.movies.App
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

    private var popularMoviesDeferred: Deferred<Flow<PagingData<Movie>?>?>? = null
    private var topRatedMoviesDeferred: Deferred<Flow<PagingData<Movie>?>?>? = null

    fun getMovies() {
        getPopularMovies()
        getTopRatedMovies()
        getUpcomingMovies()
    }

    private fun getPopularMovies() {
        popularMoviesDeferred = viewModelScope.async {
            _popularMovies.value = Result.loading(null)
            return@async try {
                delay(200)
                getPopularMoviesUseCase.invoke().cachedIn(viewModelScope)
            } catch (e: Exception) {
                println("Ex: ${e.message}")
                _popularMovies.value = Result.error(null, e.message)
                null
            }
        }
    }

    private fun getTopRatedMovies() {
        topRatedMoviesDeferred = viewModelScope.async {
            _topRatedMovies.value = Result.loading(null)
            return@async try {
                getTopRatedMoviesUseCase.invoke().cachedIn(viewModelScope)
            } catch (e: Exception) {
                println("Ex: ${e.message}")
                _topRatedMovies.value = Result.error(null, e.message)
                null
            }
        }
    }

    private fun getUpcomingMovies() {
        viewModelScope.launch {
            _upcomingMovies.value = Result.loading(null)
            try {
                getUpcomingMoviesUseCase.invoke().cachedIn(viewModelScope).collect { upcoming ->
                    // Awaiting popular movies
                    popularMoviesDeferred?.await()?.collect { popular ->
                        // Awaiting top movies
                        topRatedMoviesDeferred?.await()?.collect { topRated ->
                            // Emit popular movies
                            delay(1000)
                            _popularMovies.value = Result.success(popular)
                            // Emit top movies
                            _topRatedMovies.value = Result.success(topRated)
                            // Emit upcoming movies
                            _upcomingMovies.value = Result.success(upcoming)
                        }
                    }
                }
            } catch (e: Exception) {
                println("Ex: ${e.message}")
                _topRatedMovies.value = Result.error(null, e.message)
            }
        }
    }
}