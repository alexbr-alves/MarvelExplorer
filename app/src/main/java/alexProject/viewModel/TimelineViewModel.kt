package alexProject.viewModel

import alexProject.data.model.comic.ComicModelResponse
import alexProject.repository.MarvelRepository
import alexProject.state.ResourceState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimelineViewModel @Inject constructor(
    private val repository: MarvelRepository
) : BaseViewModel() {

    val listItems = MutableLiveData<ResourceState<ComicModelResponse>>()

    init {
        fetch()
    }

    fun fetch() = viewModelScope.launch {
        safeFetch()
    }

    private suspend fun safeFetch() = viewModelScope.launch {
        isLoading.value = true
        handleFetchResponse(
            repository.comics(orderBy = "-modified", limit = 60),
            listItems
        )
    }
}
