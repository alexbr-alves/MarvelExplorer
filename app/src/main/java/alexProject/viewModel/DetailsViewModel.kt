package alexProject.viewModel

import alexProject.data.model.FavoriteModel
import alexProject.domain.useCase.details.GetFavoriteUseCase
import alexProject.domain.useCase.details.InsertFavoriteUseCase
import alexProject.domain.useCase.favorite.DeleteFavoriteUseCase
import alexProject.state.SingleLiveEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val insertFavoriteUseCase: InsertFavoriteUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase
) : ViewModel() {

    val searchCharacter  =  SingleLiveEvent<Boolean>()

    fun insert(characterModel : FavoriteModel) = viewModelScope.launch {
        insertFavoriteUseCase(characterModel)
    }

    fun delete(characterModel : FavoriteModel) = viewModelScope.launch {
        deleteFavoriteUseCase(characterModel)
    }

    fun searchFavorite(id : Int) = viewModelScope.launch {
        val response = getFavoriteUseCase(id)
        searchCharacter.value = response
    }

}