package alexProject.viewModel.comics

import alexProject.network.response.ComicResponse
import alexProject.domain.repository.MarvelRepositoryImpl
import alexProject.state.ResourceState
import alexProject.util.Constants
import alexProject.viewModel.BaseViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicViewModel @Inject constructor(
    private val repository: MarvelRepositoryImpl
) : BaseViewModel() {

    val listItems = MutableLiveData<ResourceState<ComicResponse>>()

    fun fetch(
        verify: String? = null,
        id: Int? = null,
        titleStartsWith: String? = null,
        limit: Int = 100,
        offset: Int
    ) = viewModelScope.launch {
        safeFetch(verify, id, titleStartsWith, limit, offset)
    }

    private suspend fun safeFetch(
        verify: String? = null,
        id: Int? = null,
        titleStartsWith: String? = null,
        limit: Int = 100,
        offset: Int
    ) {
        isLoading.value = true

        when (verify) {
            Constants.CHARACTER -> {
                handleFetchResponse(
                    repository.getComicsCharacter(id ?: 0, limit, offset),
                    listItems
                )
            }

            Constants.EVENT -> {
                handleFetchResponse(
                    repository.getComicEvent(id ?: 0, limit, offset),
                    listItems
                )
            }

            else -> {
                handleFetchResponse(
                    repository.comics(titleStartsWith, limit, offset),
                    listItems
                )
            }
        }
    }
}