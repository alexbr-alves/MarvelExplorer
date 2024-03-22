package alexProject.viewModel

import alexProject.data.model.FavoriteModel
import alexProject.data.model.comic.ComicModelResponse
import alexProject.repository.DatabaseRepository
import alexProject.repository.MarvelRepository
import alexProject.state.ResourceState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimelineViewModel @Inject constructor(
    private val repository: MarvelRepository,
    private val dataRepository: DatabaseRepository
) : BaseViewModel() {

    val listItems = MutableLiveData<ResourceState<ComicModelResponse>>()

    private val _storyList =
        MutableStateFlow<ResourceState<List<FavoriteModel>>>(ResourceState.Empty())
    val storyList : StateFlow<ResourceState<List<FavoriteModel>>> = _storyList

    init {
        fetch()
    }

    fun fetch() = viewModelScope.launch {
        safeFetch()
        getStory()
    }

    private suspend fun getStory()= viewModelScope.launch {
        dataRepository.getStory().collectLatest { characters ->
            if (characters.isEmpty()) {
                _storyList.value = ResourceState.Empty()
            } else {
                _storyList.value = ResourceState.Success(characters)
            }
        }
    }

    private suspend fun safeFetch() = viewModelScope.launch {
        isLoading.value = true
        handleFetchResponse(
            repository.comics(orderBy = "-modified", limit = 60),
            listItems
        )
    }
}
