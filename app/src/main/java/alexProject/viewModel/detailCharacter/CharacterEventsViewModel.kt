package alexProject.viewModel.detailCharacter

import alexProject.R
import alexProject.data.model.event.EventModelResponse
import alexProject.repository.MarvelRepository
import alexProject.state.ResourceState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CharacterEventsViewModel @Inject constructor(
    val repository : MarvelRepository
) : ViewModel() {

    private val _eventList =
        MutableStateFlow<ResourceState<EventModelResponse>>(ResourceState.Loading())
    val eventList : StateFlow<ResourceState<EventModelResponse>> = _eventList

    fun fetchEvent(characterId : Int) = viewModelScope.launch {
        safeFetchEvent(characterId)
    }

    private suspend fun safeFetchEvent(characterId : Int) {
        _eventList.value = ResourceState.Loading()
        try {
            val response = repository.getEventsCharacter(characterId, 10, 0)
            _eventList.value = handleEventResponse(response)
        } catch (t : Throwable) {
            when (t) {
                is IOException -> _eventList.value =
                    ResourceState.Error(R.string.error_internet_connection.toString())

                else -> _eventList.value =
                    ResourceState.Error(R.string.error_data_conversion_failure.toString())
            }
        }
    }

    private fun handleEventResponse(response : Response<EventModelResponse>) : ResourceState<EventModelResponse> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }


}