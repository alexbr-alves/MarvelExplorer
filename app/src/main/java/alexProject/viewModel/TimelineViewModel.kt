package alexProject.viewModel

import alexProject.data.model.FavoriteModel
import alexProject.data.model.comic.ComicModel
import alexProject.repository.DatabaseRepository
import alexProject.repository.MarvelRepository
import alexProject.state.ResourceState
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class TimelineViewModel @Inject constructor(
    private val repository: MarvelRepository,
    private val dataRepository: DatabaseRepository
) : BaseViewModel() {

    private lateinit var listIds: List<Int>
    val listItems = MutableLiveData<List<ComicModel>>()

    private val _storyList =
        MutableStateFlow<ResourceState<List<FavoriteModel>>>(ResourceState.Empty())
    val storyList: StateFlow<ResourceState<List<FavoriteModel>>> = _storyList

    init {
        fetch()
    }

    fun fetch() = viewModelScope.launch {
        getStory()
    }

    private suspend fun getStory() = viewModelScope.launch {
        dataRepository.getStory().collectLatest { characters ->
            if (characters.isEmpty()) {
                _storyList.value = ResourceState.Empty()
            } else {
                _storyList.value = ResourceState.Success(characters)
                listIds = characters.map { it.id }
                safeFetch(listIds, getComicsLimit(characters.size))
            }
        }
    }

    private suspend fun safeFetch(list: List<Int>, limit: Int) {
        isLoading.value = true
        if (list.isNotEmpty()) {
            val comicsList = mutableListOf<ComicModel>()
            list.forEach {
                val comic = repository.getComicsCharacter(it, limit = limit)
                comic.body()?.data?.result?.let { comicResult -> comicsList.addAll(comicResult) }
            }
            if (comicsList.size < MAX_COMICS) {
                val comic = repository.comics(limit = MAX_COMICS - comicsList.size)
                comic.body()?.data?.result?.let { comicResult -> comicsList.addAll(comicResult) }
                listItems.value = comicsList
            }
        }
    }

    private fun getComicsLimit(characterCount: Int) : Int {
        val favoriteComicQuota = MAX_COMICS * 0.6
        val limit = favoriteComicQuota / characterCount
        return round(limit).toInt()
    }

    companion object {
        private const val MAX_COMICS = 60
    }

}
