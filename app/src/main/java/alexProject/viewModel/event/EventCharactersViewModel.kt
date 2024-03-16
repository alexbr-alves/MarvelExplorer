package alexProject.viewModel.event

import alexProject.data.model.character.CharacterModelResponse
import alexProject.repository.MarvelRepository
import alexProject.state.ResourceState
import alexProject.viewModel.BaseViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventCharactersViewModel @Inject constructor(
    private val repository: MarvelRepository
) : BaseViewModel() {
    val listItems = MutableLiveData<ResourceState<CharacterModelResponse>>()


    fun fetch(eventId: Int) = viewModelScope.launch {
        safeFetch(eventId)
    }

    private suspend fun safeFetch(eventId: Int) {
        isLoading.value = true
        handleFetchResponse(
            repository.getCharacterEvent(eventId, 10, 0),
            listItems
        )
    }
}