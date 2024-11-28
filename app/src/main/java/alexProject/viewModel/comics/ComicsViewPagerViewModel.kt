package alexProject.viewModel.comics


import alexProject.domain.repository.useCase.comic.GetComicCharactersUseCase
import alexProject.domain.repository.useCase.comic.GetComicEventsUseCase
import alexProject.network.response.ComicModel
import alexProject.network.response.GenericResponse
import alexProject.state.SingleLiveEvent
import alexProject.util.Constants
import alexProject.viewModel.MarvelViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicsViewPagerViewModel @Inject constructor(
    private val getComicEventsUseCase: GetComicEventsUseCase,
    private val getComicCharactersUseCase: GetComicCharactersUseCase
) : MarvelViewModel() {

    val comicList = SingleLiveEvent<GenericResponse<ComicModel>>()


    fun getComics(characterId: Int, origin: String) {
        when (origin) {
            Constants.CHARACTER -> { getComicsCharacter(characterId, 30, 0)}

            Constants.EVENT -> {
                getComimicsEvent(characterId, 30, 0)
            }
        }


    }

    private fun getComicsCharacter(id: Int, limit: Int, offset: Int) {
        handleObservable(
            observable = getComicCharactersUseCase(id, limit, offset),
            successHandler = {
                comicList.value = it
            }
        )
    }

    private fun getComimicsEvent(id: Int, limit: Int, offset: Int) {
        handleObservable(
            observable = getComicEventsUseCase(id, limit, offset),
            successHandler = {
                comicList.value = it
            }
        )
    }

}