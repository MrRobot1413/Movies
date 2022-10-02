package ua.mrrobot1413.movies.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ua.mrrobot1413.movies.data.storage.model.FavoriteMovie
import ua.mrrobot1413.movies.data.storage.model.Movie
import ua.mrrobot1413.movies.domain.useCase.favorite.GetFavoriteMoviesUseCase
import javax.inject.Inject
import ua.mrrobot1413.movies.data.network.model.Result

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
): ViewModel() {

    private val _movies = MutableLiveData<Result<List<FavoriteMovie>>>()
    val movies: LiveData<Result<List<FavoriteMovie>>> = _movies

    fun getFavoriteMovies() {
        viewModelScope.launch {
            _movies.value = Result.loading(null)
            try {
                getFavoriteMoviesUseCase.invoke().collect {
                    _movies.value = Result.success(it)
                }
            } catch (e: Exception) {
                _movies.value = Result.error(null, e.message)
            }
        }
    }
}