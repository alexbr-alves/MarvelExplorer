package alexProject.viewModel.character

import alexProject.data.model.event.EventModelResponse
import alexProject.repository.MarvelRepository
import alexProject.state.ResourceState
import alexProject.viewModel.BaseViewModel
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterEventsViewModel @Inject constructor(
    val repository: MarvelRepository
) : BaseViewModel() {
    val eventList = MutableLiveData<ResourceState<EventModelResponse>>()

    fun fetchEvent(characterId: Int) = viewModelScope.launch {
        safeFetchEvent(characterId)
    }

    private suspend fun safeFetchEvent(characterId: Int) {
        isLoading.value = true
        handleFetchResponse(
            repository.getEventsCharacter(characterId, 10, 0),
            eventList
        )
    }
}