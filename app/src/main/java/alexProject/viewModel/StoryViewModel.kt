package alexProject.viewModel

import alexProject.data.model.comic.ComicModelResponse
import alexProject.repository.MarvelRepository
import alexProject.state.ResourceState
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val repository: MarvelRepository
) : BaseViewModel() {
    val listItems = MutableLiveData<ResourceState<ComicModelResponse>>()


    fun fetch(id: Int) = viewModelScope.launch {
        safeFetch(id)
    }

    private suspend fun safeFetch(id: Int) = viewModelScope.launch {
        isLoading.value = true
        handleFetchResponse(
            repository.getComicsCharacter(id?: 0, limit = 3, offset = 0),
            listItems
        )
    }
}