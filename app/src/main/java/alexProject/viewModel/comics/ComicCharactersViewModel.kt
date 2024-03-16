package alexProject.viewModel.comics

import alexProject.data.model.character.CharacterModelResponse
import alexProject.repository.MarvelRepository
import alexProject.state.ResourceState
import alexProject.viewModel.BaseViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicCharactersViewModel @Inject constructor(
    private val repository: MarvelRepository
) : BaseViewModel() {
    val comicCharacterList = MutableLiveData<ResourceState<CharacterModelResponse>>()

    fun fetchCharacterComic(comicId: Int) = viewModelScope.launch {
        safeFetchCharacterComic(comicId)
    }

    private suspend fun safeFetchCharacterComic(characterId: Int) {
        isLoading.value = true
        handleFetchResponse(
            repository.getCharacterComics(characterId),
            comicCharacterList
        )
    }

}