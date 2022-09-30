package ua.mrrobot1413.movies.ui.detailed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.mrrobot1413.movies.data.network.model.*
import ua.mrrobot1413.movies.data.storage.model.ReminderMovie
import ua.mrrobot1413.movies.domain.useCase.detailed.*
import javax.inject.Inject

@HiltViewModel
class DetailedMovieViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val favoriteMovieUseCase: FavoriteMovieUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase,
    private val reminderMovieUseCase: ReminderMovieUseCase,
    private val createReminderMovieUseCase: CreateReminderMovieUseCase,
    private val deleteReminderUseCase: DeleteReminderUseCase
) : ViewModel() {

    private val _details = MutableLiveData<Result<DetailedMovie>>()
    val details: LiveData<Result<DetailedMovie>> = _details

    private val _similarMovies = MutableLiveData<MoviesResponse?>()
    val similarMovies: LiveData<MoviesResponse?> = _similarMovies

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

    fun getSimilarMovies(id: Int) {
        viewModelScope.launch {
            try {
                _similarMovies.value = getSimilarMoviesUseCase.invoke(id)
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    fun addToFavorite(id: Int, detailedMovie: DetailedMovie) {
        viewModelScope.launch {
            addToFavoriteUseCase.invoke(id, detailedMovie)
        }
    }

    fun removeFromFavorite(id: Int, detailedMovieId: Int) {
        viewModelScope.launch {
            removeFromFavoriteUseCase.invoke(id, detailedMovieId)
        }
    }

    fun createReminder(id: Int, movie: DetailedMovie) {
        viewModelScope.launch {
            createReminderMovieUseCase.invoke(id, movie)
        }
    }

    fun deleteReminder(id: Int) {
        viewModelScope.launch {
            deleteReminderUseCase.invoke(id)
        }
    }

    suspend fun isFavoriteMovie(id: Int) = favoriteMovieUseCase.invoke(id)
    suspend fun isReminderMovie(id: Int) = reminderMovieUseCase.invoke(id)
}