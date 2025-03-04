package alexProject.viewModel.tvShow

import alexProject.data.model.tv_show.TvShowModelResponse
import alexProject.domain.useCase.GetTvShowUseCase
import alexProject.state.SingleLiveEvent
import alexProject.viewModel.MarvelViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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