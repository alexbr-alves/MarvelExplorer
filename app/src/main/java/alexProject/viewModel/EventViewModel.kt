package alexProject.viewModel

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
class EventViewModel @Inject constructor(
    private val repository : MarvelRepository
) : ViewModel() {

    private val _eventList =
        MutableStateFlow<ResourceState<EventModelResponse>>(ResourceState.Loading())
    val eventList : StateFlow<ResourceState<EventModelResponse>> = _eventList

    init {
        fetch()
    }

    fun fetch(nameStartsWith : String? = null) = viewModelScope.launch {
        safeFetch(nameStartsWith)
    }

    private suspend fun safeFetch(nameStartsWith : String? = null) {
        try {
            val response = repository.events(nameStartsWith)
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