package alexProject.viewModel.comics

import alexProject.data.model.event.EventModelResponse
import alexProject.repository.MarvelRepository
import alexProject.state.ResourceState
import alexProject.viewModel.BaseViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicEventsViewModel @Inject constructor(
    private val repository: MarvelRepository
) : BaseViewModel() {

    val listItems = MutableLiveData<ResourceState<EventModelResponse>>()

    fun fetch(comicId: Int) = viewModelScope.launch {
        safeFetch(comicId)
    }

    private suspend fun safeFetch(characterId: Int) {
        isLoading.value = true
        handleFetchResponse(
            repository.getEventComics(characterId),
            listItems
        )
    }
}