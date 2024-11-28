package alexProject.viewModel.comics

import alexProject.domain.repository.useCase.comic.GetComicCharactersUseCase
import alexProject.domain.repository.useCase.comic.GetComicEventsUseCase
import alexProject.domain.repository.useCase.comic.GetComicsUseCase
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
class ComicViewModel @Inject constructor(
    private val getComicsUseCase: GetComicsUseCase,
    private val getComicEventsUseCase: GetComicEventsUseCase,
    private val getComicCharactersUseCase: GetComicCharactersUseCase,
) : MarvelViewModel() {

    val comicList = SingleLiveEvent<GenericResponse<ComicModel>>()

    fun getComics(
        verify: String? = null,
        id: Int? = null,
        titleStartsWith: String? = null,
        limit: Int = 100,
        offset: Int
    ) {
        when (verify) {
            Constants.CHARACTER -> {
                getComicsCharacter(id ?: 0, limit, offset)
            }

            Constants.EVENT -> {
                getComimicsEvent(id ?: 0, limit, offset)
            }

            else -> {
                getComics(titleStartsWith, limit, offset)
            }
        }
    }

    private fun getComics(titleStartsWith: String?, limit: Int, offset: Int) {
        handleObservable(
            observable = getComicsUseCase(titleStartsWith, limit, offset),
            successHandler = {
                comicList.value = it
            }
        )
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