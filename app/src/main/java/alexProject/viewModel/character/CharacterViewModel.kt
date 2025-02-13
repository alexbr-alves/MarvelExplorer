package alexProject.viewModel.character

import alexProject.domain.useCase.character.GetCharacterComicsUseCase
import alexProject.domain.useCase.character.GetCharacterEventsUseCase
import alexProject.domain.useCase.character.GetCharactersUseCase
import alexProject.network.response.CharacterResponse
import alexProject.network.response.GenericResponse
import alexProject.state.SingleLiveEvent
import alexProject.util.Constants
import alexProject.viewModel.MarvelViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getCharacterEventsUseCase: GetCharacterEventsUseCase,
    private val getCharacterComicsUseCase: GetCharacterComicsUseCase
) : MarvelViewModel() {

    val characterList = SingleLiveEvent<GenericResponse<CharacterResponse>>()
    fun fetch(
        verify: String? = null,
        id: Int? = null,
        nameStartsWith: String? = null,
        limit: Int = 100,
        offset: Int
    ) {
        when (
            verify) {
            Constants.COMIC -> {
                getCharacterComic(id ?: 0, limit, offset)
            }

            Constants.EVENT -> {
                getCharacterEvent(id ?: 0, limit, offset)
            }

            else -> {
                getCharacter(nameStartsWith, limit, offset)
            }
        }
    }

    private fun getCharacter(nameStartsWith: String?, limit: Int, offset: Int?) {
        handleObservable(
            observable = getCharactersUseCase(nameStartsWith, limit, offset),
            successHandler = {
                characterList.value = it
            }
        )
    }

    private fun getCharacterEvent(characterId: Int, limit: Int, offset: Int) {
        handleObservable(
            observable = getCharacterEventsUseCase(characterId, limit = limit, offset),
            successHandler = {
                characterList.value = it
            }
        )
    }

    private fun getCharacterComic(characterId: Int, limit: Int, offset: Int) {
        handleObservable(
            observable = getCharacterComicsUseCase(characterId, limit = limit, offset),
            successHandler = {
                characterList.value = it
            }
        )
    }

}