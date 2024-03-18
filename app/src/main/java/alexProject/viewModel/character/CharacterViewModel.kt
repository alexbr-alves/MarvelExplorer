package alexProject.viewModel.character

import alexProject.data.model.character.CharacterModelResponse
import alexProject.repository.MarvelRepository
import alexProject.state.ResourceState
import alexProject.util.Constants
import alexProject.viewModel.BaseViewModel
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: MarvelRepository
) : BaseViewModel() {

    val listItem = MutableLiveData<ResourceState<CharacterModelResponse>>()

    fun fetch(
        verify: String? = null,
        id: Int? = null,
        nameStartsWith: String? = null,
        limit: Int = 100,
        offset: Int
    ) =
        viewModelScope.launch {
            safeFetch(verify, id, nameStartsWith, limit, offset)
        }

    private suspend fun safeFetch(
        verify: String? = null,
        id: Int? = null,
        nameStartsWith: String? = null,
        limit: Int = 100,
        offset: Int
    ) {
        isLoading.value = true
        when (verify) {
            Constants.COMIC -> {
                handleFetchResponse(
                    repository.getCharacterComics(id ?: 0, limit, offset),
                    listItem
                )
            }

            Constants.EVENT -> {
                handleFetchResponse(
                    repository.getCharacterEvent(id ?: 0, limit, offset),
                    listItem
                )
            }

            else -> {
                handleFetchResponse(
                    repository.characters(nameStartsWith, limit, offset),
                    listItem
                )
            }
        }
    }

}