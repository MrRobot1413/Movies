package ua.mrrobot1413.movies.ui.reminders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ua.mrrobot1413.movies.data.network.model.Result
import ua.mrrobot1413.movies.data.storage.model.ReminderMovie
import ua.mrrobot1413.movies.domain.useCase.favorite.GetFavoriteMoviesUseCase
import ua.mrrobot1413.movies.domain.useCase.reminder.GetReminderMoviesUseCase
import javax.inject.Inject

@HiltViewModel
class RemindersViewModel @Inject constructor(
    private val getReminderMoviesUseCase: GetReminderMoviesUseCase
): ViewModel() {

    private val _movies = MutableLiveData<Result<List<ReminderMovie>>>()
    val movies: LiveData<Result<List<ReminderMovie>>> = _movies

    fun getMovies() {
        viewModelScope.launch {
            _movies.value = Result.loading(null)
            try {
                getReminderMoviesUseCase.invoke().collect {
                    _movies.value = Result.success(it)
                }
            } catch (e: Exception) {
                _movies.value = Result.error(null, e.message)
            }
        }
    }
}