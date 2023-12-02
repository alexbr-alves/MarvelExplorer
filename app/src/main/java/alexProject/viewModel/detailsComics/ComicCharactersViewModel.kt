package alexProject.viewModel.detailsComics

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
class ComicCharactersViewModel @Inject constructor(
    private val repository : MarvelRepository
) : ViewModel() {

    private val _comicCharacterList =
        MutableStateFlow<ResourceState<CharacterModelResponse>>(ResourceState.Loading())
    val comicCharacterList : StateFlow<ResourceState<CharacterModelResponse>> = _comicCharacterList

    fun fetchCharacterComic(comicId : Int) = viewModelScope.launch {
        safeFetchCharacterComic(comicId)
    }

    private suspend fun safeFetchCharacterComic(characterId : Int) {
        _comicCharacterList.value = ResourceState.Loading()
        try {
            val response = repository.getCharacterComics(characterId)
            _comicCharacterList.value = handleResponse(response)
        } catch (t : Throwable) {
            when (t) {
                is IOException -> _comicCharacterList.value =
                    ResourceState.Error(R.string.error_internet_connection.toString())

                else -> _comicCharacterList.value =
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