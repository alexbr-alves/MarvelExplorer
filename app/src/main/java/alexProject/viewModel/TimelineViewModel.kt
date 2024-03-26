package alexProject.viewModel

import alexProject.data.model.FavoriteModel
import alexProject.data.model.StoryModel
import alexProject.data.model.StoryModelData
import alexProject.data.model.ThumbnailModel
import alexProject.data.model.comic.ComicModelResponse
import alexProject.repository.DatabaseRepository
import alexProject.repository.MarvelRepository
import alexProject.state.ResourceState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimelineViewModel @Inject constructor(
    private val repository: MarvelRepository,
    private val dataRepository: DatabaseRepository
) : BaseViewModel() {

    val listItems = MutableLiveData<ResourceState<ComicModelResponse>>()
    val listComics = MutableLiveData<ResourceState<ComicModelResponse>>()
    val listStoryComics = MutableLiveData<StoryModelData>()

    private val _storyList =
        MutableStateFlow<ResourceState<List<FavoriteModel>>>(ResourceState.Empty())
    val storyList: StateFlow<ResourceState<List<FavoriteModel>>> = _storyList


    init {
        fetch()
    }

    fun fetch() = viewModelScope.launch {
        getStory()
        safeFetch()
    }

    private suspend fun getStory() = viewModelScope.launch {
        dataRepository.getStory().collectLatest { characters ->
            val idToThumbnailMap: MutableMap<Int, Pair<ThumbnailModel, String>> = mutableMapOf()
            if (characters.isEmpty()) {
                _storyList.value = ResourceState.Empty()
            } else {
                _storyList.value = ResourceState.Success(characters)
                characters.forEach {
                    idToThumbnailMap[it.id] = Pair(it.thumbnailModel, it.title)
                }
                fetchComicsForIds(idToThumbnailMap)
            }
        }
    }

    private suspend fun safeFetch() = viewModelScope.launch {
        isLoading.value = true
        handleFetchResponse(
            repository.comics(
                orderBy = "-modified",
                limit = MAX_COMICS
            ),
            listItems
        )

    }

    private suspend fun fetchComicsForIds(idsWithThumbnails: Map<Int, Pair<ThumbnailModel, String>>) {
        val storyComics: MutableList<StoryModel> = mutableListOf()
        idsWithThumbnails.forEach { (id, thumbnailWithName) ->
            val (thumbnailModel, name) = thumbnailWithName
            handleFetchResponse(
                repository.getComicsCharacter(id, limit = 3, offset = 0),
                listComics
            )
            val comics = listComics.value?.data?.data?.result ?: emptyList()
            val storyModel = StoryModel(
                id = id,
                thumbnailModel = thumbnailModel,
                name = name,
                comics = comics
            )
            storyComics.add(storyModel)
        }
        listStoryComics.value = StoryModelData(storyComics)
    }


    companion object {
        private const val MAX_COMICS = 60
    }

}
