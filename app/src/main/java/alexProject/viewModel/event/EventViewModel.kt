package alexProject.viewModel.event

import alexProject.data.model.event.EventModel
import alexProject.domain.useCase.events.GetEventsCharacterUseCase
import alexProject.domain.useCase.events.GetEventsComicsUseCase
import alexProject.domain.useCase.events.GetEventsUseCase
import alexProject.util.Constants
import alexProject.viewModel.MarvelViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val getEventsCharacterUseCase: GetEventsCharacterUseCase,
    private val getEventsComicsUseCase: GetEventsComicsUseCase
) : MarvelViewModel() {

    val listItems = MutableLiveData<List<EventModel>>()

    fun fetch(
        verify: String? = null,
        id: Int? = null,
        limit: Int = 100,
        offset: Int
    ) = viewModelScope.launch {
        safeFetch(verify, id, limit, offset)
    }

    private suspend fun safeFetch(
        verify: String? = null,
        id: Int? = null,
        limit: Int = 100,
        offset: Int
    ) {

        when (verify) {
            Constants.EVENT -> {
                handleObservable(
                    observable = getEventsCharacterUseCase(id ?: 0, limit, offset),
                    successHandler = {
                        listItems.value = it.apiResponse.entityList
                    }
                )
            }

            Constants.COMIC -> {
                handleObservable(
                    observable = getEventsComicsUseCase(id ?: 0, limit, offset),
                    successHandler = {
                        listItems.value = it.apiResponse.entityList
                    }
                )
            }

            else -> {
                handleObservable(
                    observable = getEventsUseCase(),
                    successHandler = {
                        listItems.value = it.apiResponse.entityList
                    }
                )
            }
        }
    }
}