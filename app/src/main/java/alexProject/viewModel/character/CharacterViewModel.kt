package alexProject.viewModel.character

import alexProject.R
import alexProject.data.model.character.CharacterModelResponse
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
class CharacterViewModel @Inject constructor(
    private val repository : MarvelRepository
) : ViewModel() {

    private val _list =
        MutableStateFlow<ResourceState<CharacterModelResponse>>(ResourceState.Loading())
    val list : StateFlow<ResourceState<CharacterModelResponse>> = _list


    fun fetch(
        verify : String? = null,
        id : Int? = null,
        nameStartsWith : String? = null,
        limit : Int = 100,
        offset : Int
    ) =
        viewModelScope.launch {
            safeFetch(verify, id, nameStartsWith, limit, offset)
        }

    private suspend fun safeFetch(
        verify : String? = null,
        id : Int? = null,
        nameStartsWith : String? = null,
        limit : Int = 100,
        offset : Int
    ) {
        _list.value = ResourceState.Loading()
        try {
            val response: Response<CharacterModelResponse> = when(verify) {
                Constants.COMIC -> {
                    repository.getCharacterComics(id?:0, limit, offset)
                }
                Constants.EVENT -> {
                    repository.getCharacterEvent(id?:0, limit, offset)
                }
                else -> {
                    repository.characters(nameStartsWith, limit, offset)
                }
            }
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