package ua.mrrobot1413.movies.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ua.mrrobot1413.movies.data.network.model.MovieResponseModel
import ua.mrrobot1413.movies.data.network.model.Result
import ua.mrrobot1413.movies.domain.useCase.home.SearchMoviesUseCase
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _movies = MutableLiveData<Result<PagingData<MovieResponseModel>?>?>()
    val movies: LiveData<Result<PagingData<MovieResponseModel>?>?> = _movies


    fun search(query: String) {
        viewModelScope.launch {
            _movies.value = Result.loading(null)
            try {
                searchMoviesUseCase.invoke(query).cachedIn(viewModelScope).collectLatest {
                    _movies.value = Result.success(it)
                }
            } catch (e: Exception) {
                _movies.value = Result.error(null, e.message)
            }
        }
    }
}