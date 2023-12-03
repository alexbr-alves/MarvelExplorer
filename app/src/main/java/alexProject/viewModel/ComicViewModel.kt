package alexProject.viewModel

import alexProject.R
import alexProject.data.model.comic.ComicModelResponse
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
class ComicViewModel @Inject constructor(
    private val repository : MarvelRepository
) : ViewModel() {

    private val _comicList =
        MutableStateFlow<ResourceState<ComicModelResponse>>(ResourceState.Loading())
    val comicList : StateFlow<ResourceState<ComicModelResponse>> = _comicList


    fun fetch(
        verify : String? = null,
        id : Int? = null,
        titleStartsWith : String? = null,
        limit : Int = 100,
        offset : Int? = 0
    ) = viewModelScope.launch {
        safeFetch(verify, id, titleStartsWith, limit, offset)
    }

    private suspend fun safeFetch(
        verify : String? = null, id : Int? = null,
        titleStartsWith : String? = null,
        limit : Int = 100,
        offset : Int? = 0
    ) {
        try {
            _comicList.value = ResourceState.Loading()
            val response : Response<ComicModelResponse> = when (verify) {
                Constants.CHARACTER -> {
                    repository.getComicsCharacter(id ?: 0)
                }
                Constants.EVENT -> {
                    repository.getComicEvent(id ?: 0)
                }
                else -> {
                    repository.comics(titleStartsWith, limit, offset)
                }
            }

            _comicList.value = handleResponse(response)
        } catch (t : Throwable) {
            when (t) {
                is IOException -> _comicList.value =
                    ResourceState.Error(R.string.error_internet_connection.toString())

                else -> _comicList.value =
                    ResourceState.Error(R.string.error_data_conversion_failure.toString())
            }
        }
    }

    private fun handleResponse(response : Response<ComicModelResponse>) : ResourceState<ComicModelResponse> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }

}