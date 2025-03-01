package alexProject.viewModel.character

import alexProject.domain.useCase.character.GetCharacterComicsUseCase
import alexProject.domain.useCase.character.GetCharacterEventsUseCase
import alexProject.network.response.CharacterResponse
import alexProject.network.response.GenericResponse
import alexProject.state.SingleLiveEvent
import alexProject.util.Constants
import alexProject.viewModel.MarvelViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewPagerViewModel @Inject constructor(
    private val getCharacterEventsUseCase: GetCharacterEventsUseCase,
    private val getCharacterComicsUseCase: GetCharacterComicsUseCase,
) : MarvelViewModel() {
    val comicCharacterList = SingleLiveEvent<GenericResponse<CharacterResponse>>()

    fun getCharacter(characterId: Int, origin: String) {
        when (origin) {
            Constants.COMIC -> {
                getCharacterComic(characterId, limit = 30)
            }

            Constants.EVENT -> {
                getCharacterEvent(characterId, limit = 30)
            }
        }
    }

    private fun getCharacterEvent(characterId: Int, limit: Int) {
        handleObservable(
            observable = getCharacterEventsUseCase(characterId, limit = limit),
            successHandler = {
                comicCharacterList.value = it
            }
        )
    }

    private fun getCharacterComic(characterId: Int, limit: Int) {
        handleObservable(
            observable = getCharacterComicsUseCase(characterId, limit = limit),
            successHandler = {
                comicCharacterList.value = it
            }
        )
    }
}