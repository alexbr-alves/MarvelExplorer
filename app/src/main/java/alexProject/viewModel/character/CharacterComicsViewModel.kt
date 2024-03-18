package alexProject.viewModel.character


import alexProject.data.model.comic.ComicModelResponse
import alexProject.repository.MarvelRepository
import alexProject.state.ResourceState
import alexProject.viewModel.BaseViewModel
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterComicsViewModel @Inject constructor(
    private val repository: MarvelRepository
) : BaseViewModel() {

    val comicList = MutableLiveData<ResourceState<ComicModelResponse>>()

    fun fetchComic(characterId: Int) = viewModelScope.launch {
        safeFetchComic(characterId)
    }

    private suspend fun safeFetchComic(characterId: Int) {
        isLoading.value = true
        handleFetchResponse(
            repository.getComicsCharacter(characterId, 10, 0),
            comicList
        )
    }

}