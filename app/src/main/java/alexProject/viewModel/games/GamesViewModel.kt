package alexProject.viewModel.games

import alexProject.data.model.games.GamesModelResponse
import alexProject.repository.GithubRepository
import alexProject.repository.MarvelRepository
import alexProject.state.ResourceState
import alexProject.viewModel.BaseViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val repository: GithubRepository
) : BaseViewModel() {

    val listItems = MutableLiveData<ResourceState<GamesModelResponse>>()

    init {
        fetch()
    }

    fun fetch() = viewModelScope.launch {
        safeFetch()
    }

    private suspend fun safeFetch() = viewModelScope.launch {
        isLoading.value = true
        handleFetchResponse(repository.getGames(), listItems)
    }

}