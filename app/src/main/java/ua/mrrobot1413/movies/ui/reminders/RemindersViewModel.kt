package ua.mrrobot1413.movies.ui.reminders

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ua.mrrobot1413.movies.domain.useCase.favorite.GetFavoriteMoviesUseCase
import javax.inject.Inject

@HiltViewModel
class RemindersViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
): ViewModel() {
}