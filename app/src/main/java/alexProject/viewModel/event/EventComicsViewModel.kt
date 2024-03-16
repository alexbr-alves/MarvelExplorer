package alexProject.viewModel.event

import alexProject.data.model.comic.ComicModelResponse
import alexProject.repository.MarvelRepository
import alexProject.state.ResourceState
import alexProject.viewModel.BaseViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventComicsViewModel @Inject constructor(
    private val repository: MarvelRepository
) : BaseViewModel() {

    val listItems = MutableLiveData<ResourceState<ComicModelResponse>>()


    fun fetch(eventId: Int) = viewModelScope.launch {
        safeFetchComic(eventId)
    }

    private suspend fun safeFetchComic(eventId: Int) {

        isLoading.value = true
        handleFetchResponse(
            repository.getComicEvent(eventId, 10, 0),
            listItems
        )
    }
}