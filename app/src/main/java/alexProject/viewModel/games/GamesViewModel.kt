package alexProject.viewModel.games

import alexProject.data.model.games.GamesModelResponse
import alexProject.domain.useCase.GetGamesUseCase
import alexProject.state.SingleLiveEvent
import alexProject.viewModel.MarvelViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val getGamesUseCase: GetGamesUseCase
) : MarvelViewModel() {
    val listItems = SingleLiveEvent<GamesModelResponse>()

    fun getGames() {
        handleObservable(
            observable = getGamesUseCase(),
            successHandler = {
                listItems.value = it
            }
        )
    }
}