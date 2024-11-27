package alexProject.viewModel.character

import alexProject.network.response.CharacterResponse
import alexProject.domain.repository.useCase.GetCharactersUseCase
import alexProject.state.SingleLiveEvent
import alexProject.viewModel.MarvelViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
) : MarvelViewModel() {

    val listItem = SingleLiveEvent<CharacterResponse>()
    var firshLoading = false
    fun fetch(
        verify: String? = null,
        id: Int? = null,
        nameStartsWith: String? = null,
        limit: Int = 100,
        offset: Int
    ) {
        when (verify) {
//            Constants.COMIC -> {
//                handleFetchResponse(
//                    repository.getCharacterComics(id ?: 0, limit, offset),
//                    listItem
//                )
//            }
//
//            Constants.EVENT -> {
//                handleFetchResponse(
//                    repository.getCharacterEvent(id ?: 0, limit, offset),
//                    listItem
//                )
//            }

            else -> {
                handleObservable(getCharactersUseCase(nameStartsWith, limit, offset),
                    successHandler = {
                    listItem.value = it
                })
            }
        }
    }

}