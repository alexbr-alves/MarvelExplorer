package alexProject.viewModel.event

import alexProject.data.model.event.EventModel
import alexProject.domain.useCase.events.GetEventsCharacterUseCase
import alexProject.domain.useCase.events.GetEventsComicsUseCase
import alexProject.domain.useCase.events.GetEventsUseCase
import alexProject.state.ResourceState
import alexProject.util.Constants
import alexProject.viewModel.MarvelViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewPagerViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val getEventsCharacterUseCase: GetEventsCharacterUseCase,
    private val getEventsComicsUseCase: GetEventsComicsUseCase
) : MarvelViewModel() {
    val eventList = MutableLiveData<List<EventModel>>()

    fun fetchEvent(characterId: Int, origin: String) = viewModelScope.launch {
        safeFetchEvent(characterId, origin)
    }

    private suspend fun safeFetchEvent(characterId: Int, origin: String) {
        when (origin) {
            Constants.CHARACTER -> {
                handleObservable(
                    observable = getEventsCharacterUseCase(characterId, 30, 0),
                    successHandler = {
                        eventList.value = it.apiResponse.entityList
                    }
                )
            }

            Constants.COMIC -> {
                handleObservable(
                    observable = getEventsComicsUseCase(characterId, 30, 0),
                    successHandler = {
                        eventList.value = it.apiResponse.entityList
                    }
                )
            }
        }
    }
}