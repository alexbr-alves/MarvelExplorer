package alexProject.viewModel.event

import alexProject.data.model.event.EventModelResponse
import alexProject.repository.MarvelRepository
import alexProject.state.ResourceState
import alexProject.util.Constants
import alexProject.viewModel.BaseViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val repository: MarvelRepository
) : BaseViewModel() {

    val listItems = MutableLiveData<ResourceState<EventModelResponse>>()

    fun fetch(
        verify: String? = null,
        id: Int? = null,
        titleStartsWith: String? = null,
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
        isLoading.value = true
        when (verify) {
            Constants.EVENT -> {
                handleFetchResponse(
                    repository.getEventsCharacter(id ?: 0, limit, offset),
                    listItems
                )
            }

            Constants.COMIC -> {
                handleFetchResponse(
                    repository.getEventComics(id ?: 0, limit, offset),
                    listItems
                )
            }

            else -> {
                handleFetchResponse(
                    repository.events(),
                    listItems
                )
            }
        }
    }
}