package alexProject.viewModel.movies

import alexProject.data.model.movies.MoviesModelResponse
import alexProject.repository.MarvelRepository
import alexProject.state.ResourceState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository : MarvelRepository
) : ViewModel() {

    private val _listItems =
        MutableStateFlow<ResourceState<MoviesModelResponse>>(ResourceState.Loading())
    val listItems: StateFlow<ResourceState<MoviesModelResponse>> = _listItems

    init {
        fetch()
    }

    fun fetch() = viewModelScope.launch {
        safeFetch()
    }

    private suspend fun safeFetch() = viewModelScope.launch {
        try {
            _listItems.value = ResourceState.Loading()
            val response: Response<MoviesModelResponse> = repository.getMovies()
            _listItems.value = handleResponse(response)
        } catch (t: Throwable) {
            _listItems.value = ResourceState.Error(t.message ?: "Unknown error occurred")
        }
    }


    private fun handleResponse(response: Response<MoviesModelResponse>): ResourceState<MoviesModelResponse> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }
}