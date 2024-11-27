package alexProject.viewModel.group

import alexProject.data.model.group.GroupModelResponse
import alexProject.domain.repository.GithubRepository
import alexProject.state.ResourceState
import alexProject.viewModel.BaseViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val repository: GithubRepository
) : BaseViewModel() {
    val listItems = MutableLiveData<ResourceState<GroupModelResponse>>()

    init {
        fetch()
    }

    fun fetch() = viewModelScope.launch {
        safeFetch()
    }

    private suspend fun safeFetch() = viewModelScope.launch {
        isLoading.value = true
        handleFetchResponse(
            repository.getGroup(),
            listItems
        )
    }
}