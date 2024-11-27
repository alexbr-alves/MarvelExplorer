package alexProject.viewModel.character

import alexProject.data.model.character.CharacterResponse
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
class CharactersViewPagerViewModel @Inject constructor(
    private val repository: MarvelRepositoryImpl
) : BaseViewModel() {
    val comicCharacterList = MutableLiveData<ResourceState<CharacterResponse>>()

    fun fetchCharacterComic(comicId: Int, origin: String) = viewModelScope.launch {
        safeFetchCharacterComic(comicId, origin)
    }

    private suspend fun safeFetchCharacterComic(characterId: Int, origin: String) {
        isLoading.value = true
        when (origin) {
            Constants.COMIC -> {
                handleFetchResponse(
                    repository.getCharacterComics(characterId, limit = 30),
                    comicCharacterList
                )
            }

            Constants.EVENT -> {
                handleFetchResponse(
                    repository.getCharacterEvent(characterId, limit = 30),
                    comicCharacterList
                )
            }
        }
    }
}