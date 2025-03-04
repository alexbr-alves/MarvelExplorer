package alexProject.viewModel.group

import alexProject.data.model.group.GroupModelResponse
import alexProject.domain.useCase.GetGroupUseCase
import alexProject.state.SingleLiveEvent
import alexProject.viewModel.MarvelViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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