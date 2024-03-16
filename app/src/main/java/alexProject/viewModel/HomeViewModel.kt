package alexProject.viewModel

import alexProject.R
import alexProject.data.model.DestinationHome
import alexProject.data.model.MenuHomeModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {
    val list = MutableLiveData<List<MenuHomeModel>>()

    init {
        fetch()
    }

    fun fetch() = viewModelScope.launch {
        safeFetch()
    }

    private suspend fun safeFetch() = viewModelScope.launch {
        list.value = listOf(
            MenuHomeModel(
                title = R.string.comics,
                image = R.drawable.ic_comics,
                destination = DestinationHome.COMIC
            ),
            MenuHomeModel(
                title = R.string.title_fragment_characters,
                image = R.drawable.ic_characters,
                destination = DestinationHome.CHARACTERS
            ),
            MenuHomeModel(
                title = R.string.title_fragment_movies,
                image = R.drawable.ic_movies,
                destination = DestinationHome.MOVIES
            ),
            MenuHomeModel(
                title = R.string.title_fragment_tv_show,
                image = R.drawable.ic_tv_show,
                destination = DestinationHome.TV_SHOW
            ),
            MenuHomeModel(
                title = R.string.title_fragment_games,
                image = R.drawable.ic_games,
                destination = DestinationHome.GAMES
            ),
            MenuHomeModel(
                title = R.string.title_fragment_teams_and_groups,
                image = R.drawable.ic_teams,
                destination = DestinationHome.TEAMS
            )
        )
    }
}