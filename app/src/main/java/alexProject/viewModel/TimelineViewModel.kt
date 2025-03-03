package alexProject.viewModel

import alexProject.data.model.FavoriteModel
import alexProject.data.model.StoryModel
import alexProject.data.model.StoryModelData
import alexProject.data.model.ThumbnailModel
import alexProject.domain.useCase.timeline.GetComicsCharacterUseCase
import alexProject.domain.useCase.timeline.GetComicsUseCase
import alexProject.domain.useCase.timeline.GetStoryUseCase
import alexProject.network.response.ComicResponse
import alexProject.network.response.GenericResponse
import alexProject.state.SingleLiveEvent
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimelineViewModel @Inject constructor(
    private val getComicsUseCase: GetComicsUseCase,
    private val getComicsCharacterUseCase: GetComicsCharacterUseCase,
    private val getStoryUseCase: GetStoryUseCase
) : MarvelViewModel() {

    val listItems = SingleLiveEvent<GenericResponse<ComicResponse>>()
    val listComics = SingleLiveEvent<List<ComicResponse>>()
    val listStoryComics = SingleLiveEvent<StoryModelData>()

    val storyList = SingleLiveEvent<List<FavoriteModel>>()

    fun getStory() = viewModelScope.launch {
        getStoryUseCase().collectLatest { characters ->
            val idToThumbnailMap: MutableMap<Int, Pair<ThumbnailModel, String>> = mutableMapOf()
            if (characters.isNotEmpty()) {
                storyList.value = characters
                characters.forEach { result ->
                    idToThumbnailMap[result.id] = Pair(result.thumbnailModel, result.title)
                }
                fetchComicsForIds(idToThumbnailMap)
            }
        }
    }

    fun getComics() = viewModelScope.launch {
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
