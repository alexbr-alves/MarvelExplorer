package alexProject.viewModel.tvShow

import alexProject.data.model.tv_show.TvShowModelResponse
import alexProject.domain.useCase.GetTvShowUseCase
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
class TvShowViewModel @Inject constructor(
    private val getTvShowUseCase: GetTvShowUseCase
) : MarvelViewModel() {

    val listItems = SingleLiveEvent<TvShowModelResponse>()

    fun getTvShow() {
        handleObservable(
            observable = getTvShowUseCase(),
            successHandler = {
                listItems.value = it
            }
        )
    }
}