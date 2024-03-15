package alexProject.viewModel.movies

import alexProject.data.model.movies.MoviesModelResponse
import alexProject.repository.MarvelRepository
import alexProject.state.ResourceState
import alexProject.state.SingleLiveEvent
import alexProject.viewModel.BaseViewModel
import androidx.lifecycle.MutableLiveData
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
    private val repository: MarvelRepository
) : BaseViewModel() {

    val listItems = MutableLiveData<ResourceState<MoviesModelResponse>>(ResourceState.Loading())
    init {
        fetch()
    }

    fun fetch() = viewModelScope.launch {
        safeFetch()
    }

    private suspend fun safeFetch() = viewModelScope.launch {
        when (val response = handleResponse(repository.getMovies())) {
            is ResourceState.Success -> {
                listItems.value = response
                isLoading.value = false
            }
            is ResourceState.Error -> {
                onError.value = response.message
                isLoading.value = false
            }
            is ResourceState.Loading -> isLoading.value = true
            is ResourceState.Empty -> {
                isLoading.value = false
                onError.value = response.message
            }
        }
    }

}