package alexProject.viewModel

import alexProject.R
import alexProject.data.model.event.EventModelResponse
import alexProject.repository.MarvelRepository
import alexProject.state.ResourceState
import alexProject.util.Constants
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
class EventViewModel @Inject constructor(
    private val repository : MarvelRepository
) : ViewModel() {

    private val _eventList =
        MutableStateFlow<ResourceState<EventModelResponse>>(ResourceState.Loading())
    val eventList : StateFlow<ResourceState<EventModelResponse>> = _eventList

    fun fetch(
        verify : String? = null,
        id : Int? = null,
        titleStartsWith : String? = null,
        limit : Int = 100,
        offset : Int
    ) = viewModelScope.launch {
        safeFetch(verify, id, titleStartsWith, limit, offset)
    }

    private suspend fun safeFetch(
        verify : String? = null,
        id : Int? = null,
        titleStartsWith : String? = null,
        limit : Int = 100,
        offset : Int
    ) {
        try {
            _eventList.value = ResourceState.Loading()
            val response : Response<EventModelResponse> = when (verify) {
                Constants.CHARACTER -> {
                    repository.getEventsCharacter(id ?: 0, limit, offset)
                }
                Constants.COMIC -> {
                    repository.getEventComics(id ?: 0, limit, offset)
                }
                else -> {
                    repository.events(titleStartsWith, limit, offset)
                }
            }
            _eventList.value = handleResponse(response)
        } catch (t : Throwable) {
            when (t) {
                is IOException -> _eventList.value =
                    ResourceState.Error(R.string.error_internet_connection.toString())

                else -> _eventList.value =
                    ResourceState.Error(R.string.error_data_conversion_failure.toString())
            }
        }
    }

    private fun handleResponse(response : Response<EventModelResponse>) : ResourceState<EventModelResponse> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }

}