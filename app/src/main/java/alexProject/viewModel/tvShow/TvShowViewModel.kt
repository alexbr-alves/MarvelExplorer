package alexProject.viewModel.tvShow

import alexProject.data.model.tv_show.TvShowModelResponse
import alexProject.state.ResourceState
import alexProject.viewModel.BaseViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(
    //private val repository: GithubRepository
) : BaseViewModel() {

    val listItems = MutableLiveData<ResourceState<TvShowModelResponse>>()

    init {
        fetch()
    }

    fun fetch() = viewModelScope.launch {
        safeFetch()
    }

    private suspend fun safeFetch() = viewModelScope.launch {
        isLoading.value = true
//        handleFetchResponse(
//            repository.getTvShow(),
//            listItems
//        )
    }
}