package alexProject.viewModel.comics


import alexProject.data.model.comic.ComicModelResponse
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
class ComicsViewPagerViewModel @Inject constructor(
    private val repository: MarvelRepositoryImpl
) : BaseViewModel() {

    val comicList = MutableLiveData<ResourceState<ComicModelResponse>>()

    fun fetchComic(characterId: Int, origin: String) = viewModelScope.launch {
        safeFetchComic(characterId, origin)
    }

    private suspend fun safeFetchComic(characterId: Int, origin: String) {
        isLoading.value = true

        when (origin) {
            Constants.CHARACTER -> {
                handleFetchResponse(
                    repository.getComicsCharacter(characterId, 30, 0),
                    comicList
                )
            }

            Constants.EVENT -> {
                handleFetchResponse(
                    repository.getComicEvent(characterId, 30, 0),
                    comicList
                )
            }
        }


    }

}