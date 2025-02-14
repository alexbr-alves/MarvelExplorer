package alexProject.viewModel

import alexProject.data.model.FavoriteModel
import alexProject.data.model.StoryModel
import alexProject.data.model.StoryModelData
import alexProject.data.model.ThumbnailModel
import alexProject.domain.repository.DatabaseRepository
import alexProject.domain.repository.MarvelRepositoryImpl
import alexProject.domain.useCase.timeline.GetComicsCharacterUseCase
import alexProject.domain.useCase.timeline.GetComicsUseCase
import alexProject.domain.useCase.timeline.GetStoryUseCase
import alexProject.network.response.ComicModel
import alexProject.network.response.GenericResponse
import alexProject.state.ResourceState
import alexProject.state.SingleLiveEvent
import android.util.Log
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
    private val getComicsUseCase: GetComicsUseCase,
    private val getComicsCharacterUseCase: GetComicsCharacterUseCase,
    private val getStoryUseCase: GetStoryUseCase
) : MarvelViewModel() {

    init {
        fetch()
    }

    fun fetch() = viewModelScope.launch {
        getStory()
        getComics()
    }

    val listItems = SingleLiveEvent<GenericResponse<ComicModel>>()
    val listComics = SingleLiveEvent<List<ComicModel>>()
    val listStoryComics = MutableLiveData<StoryModelData>()

    val storyList = SingleLiveEvent<List<FavoriteModel>>()

    private fun getStory() = viewModelScope.launch {
        val idToThumbnailMap: MutableMap<Int, Pair<ThumbnailModel, String>> = mutableMapOf()
        val story = getStoryUseCase()
        if (story.isNotEmpty()) {
            storyList.value = story
            story.forEach {
                idToThumbnailMap[it.id] = Pair(it.thumbnailModel, it.title)
            }
            fetchComicsForIds(idToThumbnailMap)
        }
    }

    fun getComics() {
        handleObservable(
            observable = getComicsUseCase(),
            successHandler = {
                listItems.value = it
            }
        )

    }

    private fun fetchComicsForIds(idsWithThumbnails: Map<Int, Pair<ThumbnailModel, String>>) = viewModelScope.launch {
        val storyComics: MutableList<StoryModel> = mutableListOf()
        idsWithThumbnails.forEach { (id, thumbnailWithName) ->
            val (thumbnailModel, name) = thumbnailWithName
            handleObservable(
                observable = getComicsCharacterUseCase(id, limit = 3, offset = 0),
                successHandler = {
                    if (it.apiResponse.entityList.isNotEmpty()) {
                        val storyModel = StoryModel(
                            id = id,
                            thumbnailModel = thumbnailModel,
                            name = name,
                            comics = it.apiResponse.entityList
                        )
                        storyComics.add(storyModel)
                    }
                }
            )
        }
        listStoryComics.value = StoryModelData(storyComics)
    }


    companion object {
        private const val MAX_COMICS = 60
    }

}
