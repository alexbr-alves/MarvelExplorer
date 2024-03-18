package alexProject.viewModel.event

import alexProject.data.model.event.EventModelResponse
import alexProject.repository.MarvelRepository
import alexProject.state.ResourceState
import alexProject.util.Constants
import alexProject.viewModel.BaseViewModel
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewPagerViewModel @Inject constructor(
    val repository: MarvelRepository
) : BaseViewModel() {
    val eventList = MutableLiveData<ResourceState<EventModelResponse>>()

    fun fetchEvent(characterId: Int, origin: String) = viewModelScope.launch {
        safeFetchEvent(characterId, origin)
        Log.i("Brito", "ViewModel: $origin")
    }

    private suspend fun safeFetchEvent(characterId: Int, origin: String) {
        isLoading.value = true
        when(origin) {
            Constants.CHARACTER -> {
                handleFetchResponse(
                    repository.getEventsCharacter(characterId, 10, 0),
                    eventList
                )
            }
            Constants.COMIC -> {
                handleFetchResponse(
                    repository.getEventComics(characterId, 10, 0),
                    eventList
                )
            }
        }


    }
}