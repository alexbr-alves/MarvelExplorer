package alexProject.viewModel.detailEvent

import alexProject.R
import alexProject.data.model.character.CharacterModelResponse
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
class EventCharactersViewModel @Inject constructor(
    private val repository : MarvelRepository
) : ViewModel() {

    private val _list =
        MutableStateFlow<ResourceState<CharacterModelResponse>>(ResourceState.Loading())
    val list : StateFlow<ResourceState<CharacterModelResponse>> = _list

    fun fetch(eventId : Int) = viewModelScope.launch {
        safeFetch(eventId)
    }

    private suspend fun safeFetch(eventId : Int) {
        _list.value = ResourceState.Loading()
        try {
            val response = repository.getCharacterEvent(eventId, 10, 0)
            _list.value = handleResponse(response)
        } catch (t : Throwable) {
            when (t) {
                is IOException -> _list.value =
                    ResourceState.Error(R.string.error_internet_connection.toString())

                else -> _list.value =
                    ResourceState.Error(R.string.error_data_conversion_failure.toString())
            }
        }
    }

    private fun handleResponse(response : Response<CharacterModelResponse>) : ResourceState<CharacterModelResponse> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }

}