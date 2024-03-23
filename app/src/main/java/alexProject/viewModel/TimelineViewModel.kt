package alexProject.viewModel

import alexProject.data.model.FavoriteModel
import alexProject.data.model.comic.ComicModel
import alexProject.data.model.comic.ComicModelResponse
import alexProject.repository.DatabaseRepository
import alexProject.repository.MarvelRepository
import alexProject.state.ResourceState
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class TimelineViewModel @Inject constructor(
    private val repository: MarvelRepository,
    private val dataRepository: DatabaseRepository
) : BaseViewModel() {

    val listItems = MutableLiveData<ResourceState<ComicModelResponse>>()

    private val _storyList =
        MutableStateFlow<ResourceState<List<FavoriteModel>>>(ResourceState.Empty())
    val storyList: StateFlow<ResourceState<List<FavoriteModel>>> = _storyList


    init {
        fetch()
    }

    fun fetch() = viewModelScope.launch {
        getStory()
        safeFetch()
    }

    private suspend fun getStory() = viewModelScope.launch {
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
            repository.comics(orderBy = "-modified", limit = MAX_COMICS),
            listItems
        )

    }

    companion object {
        private const val MAX_COMICS = 60
    }

}
