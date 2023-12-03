package alexProject.viewModel.detailCharacter

import alexProject.R
import alexProject.data.model.comic.ComicModelResponse
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
class CharacterComicsViewModel @Inject constructor(
    private val repository : MarvelRepository
) : ViewModel() {
    private val _comicList =
        MutableStateFlow<ResourceState<ComicModelResponse>>(ResourceState.Loading())
    val comicList : StateFlow<ResourceState<ComicModelResponse>> = _comicList

    fun fetchComic(characterId : Int) = viewModelScope.launch {
        safeFetchComic(characterId)
    }

    private suspend fun safeFetchComic(characterId : Int) {
        _comicList.value = ResourceState.Loading()
        try {
            val response = repository.getComicsCharacter(characterId, 30, 0)
            _comicList.value = handleComicResponse(response)
        } catch (t : Throwable) {
            when (t) {
                is IOException -> _comicList.value =
                    ResourceState.Error(R.string.error_internet_connection.toString())

                else -> _comicList.value =
                    ResourceState.Error(R.string.error_data_conversion_failure.toString())
            }
        }
    }

    private fun handleComicResponse(response : Response<ComicModelResponse>) : ResourceState<ComicModelResponse> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }
}