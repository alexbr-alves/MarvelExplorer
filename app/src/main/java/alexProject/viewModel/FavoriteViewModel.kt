package alexProject.viewModel

import alexProject.data.model.FavoriteModel
import alexProject.domain.useCase.favorite.DeleteFavoriteUseCase
import alexProject.domain.useCase.favorite.GetAllFatoritesUseCase
import alexProject.state.SingleLiveEvent
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getAllFatoritesUseCase: GetAllFatoritesUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
) : MarvelViewModel() {

    val favorites = SingleLiveEvent<List<FavoriteModel>>()
    val onEmptyList = SingleLiveEvent<Void?>()

    init {
        fetch()
    }

    private fun fetch() = viewModelScope.launch {
        getAllFatoritesUseCase().collectLatest { characters ->
            if (characters.isNotEmpty()) {
                favorites.value = characters
            } else {
                onEmptyList.call()
            }
        }
    }

    fun delete(characterModel : FavoriteModel) = viewModelScope.launch {
        deleteFavoriteUseCase(characterModel)
    }
}