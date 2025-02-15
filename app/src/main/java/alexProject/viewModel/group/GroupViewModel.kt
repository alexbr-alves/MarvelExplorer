package alexProject.viewModel.group

import alexProject.data.model.group.GroupModelResponse
import alexProject.domain.useCase.GetGroupUseCase
import alexProject.state.ResourceState
import alexProject.state.SingleLiveEvent
import alexProject.viewModel.BaseViewModel
import alexProject.viewModel.MarvelViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val getGroupUseCase: GetGroupUseCase
) : MarvelViewModel() {
    val listItems = SingleLiveEvent<GroupModelResponse>()

    fun getGroups() {
        handleObservable(
            observable = getGroupUseCase(),
            successHandler = {
                listItems.value = it
            }
        )
    }
}