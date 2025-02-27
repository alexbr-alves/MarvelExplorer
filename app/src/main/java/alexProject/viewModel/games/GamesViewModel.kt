package alexProject.viewModel.games

import alexProject.data.model.games.GamesModelResponse
import alexProject.domain.useCase.GetGamesUseCase
import alexProject.state.ResourceState
import alexProject.state.SingleLiveEvent
import alexProject.viewModel.MarvelViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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