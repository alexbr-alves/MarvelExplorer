package alexProject.viewModel

import alexProject.data.model.FavoriteModel
import alexProject.data.model.StoryModel
import alexProject.data.model.StoryModelData
import alexProject.data.model.ThumbnailModel
import alexProject.domain.useCase.timeline.GetComicTimelineDaoUseCase
import alexProject.domain.useCase.timeline.GetComicsCharacterUseCase
import alexProject.domain.useCase.timeline.GetComicsUseCase
import alexProject.domain.useCase.timeline.GetStoryUseCase
import alexProject.domain.useCase.timeline.InsertComicTimelineDaoUseCase
import alexProject.network.response.ComicResponse
import alexProject.state.SingleLiveEvent
import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TimelineViewModel @Inject constructor(
    private val getComicsUseCase: GetComicsUseCase,
    private val getComicsCharacterUseCase: GetComicsCharacterUseCase,
    private val getStoryUseCase: GetStoryUseCase,
    private val getComicTimelineDaoUseCase: GetComicTimelineDaoUseCase,
    private val insertComicTimelineDaoUseCase: InsertComicTimelineDaoUseCase
) : MarvelViewModel() {

    val listComics = SingleLiveEvent<List<ComicResponse>>()
    val listStoryComics = SingleLiveEvent<StoryModelData>()

    val storyList = SingleLiveEvent<List<FavoriteModel>>()

    fun getStory() = viewModelScope.launch {
        val storyComics: MutableList<StoryModel> = mutableListOf()
        getStoryUseCase().collectLatest { characters ->
            if (characters.isNotEmpty()) {
                storyList.value = characters
                characters.forEach {
                    val storyModel = StoryModel(
                        id = it.id,
                        thumbnailModel = it.thumbnailModel,
                        name = it.title,
                        comics = it.list ?: emptyList()
                    )
                    storyComics.add(storyModel)
                }
            }
            listStoryComics.value = StoryModelData(storyComics)
        }
    }

    fun getComics() = viewModelScope.launch {
        val localComic = withContext(Dispatchers.IO) {
            getComicTimelineDaoUseCase()
        }
        Log.d("AlexBrito", "localComic : ${localComic}")
        if (localComic.isNotEmpty()) {
            viewModelScope.launch {
                handleObservable(
                    observable = getComicsUseCase(limit = 1),
                    successHandler = {
                        Log.d("AlexBrito", "data : ${localComic[0].id}")
                        Log.d("AlexBrito", "api : ${it.apiResponse.entityList[0].id}")
                        if (localComic[0].id == it.apiResponse.entityList[0].id) {
                            viewModelScope.launch {
                                listComics.value = localComic
                            }
                        } else {
                            handleObservable(
                                observable = getComicsUseCase(60),
                                successHandler = { response ->
                                    viewModelScope.launch {
                                        withContext(Dispatchers.IO) {
                                            insertComicTimelineDaoUseCase(response.apiResponse.entityList)
                                        }
                                    }
                                    listComics.value = response.apiResponse.entityList
                                }
                            )
                        }
                    }
                )
            }

        } else {
            handleObservable(
                observable = getComicsUseCase(60),
                successHandler = { response ->
                    viewModelScope.launch {
                        withContext(Dispatchers.IO) {
                            insertComicTimelineDaoUseCase(response.apiResponse.entityList)
                        }
                    }
                    listComics.value = response.apiResponse.entityList
                }
            )
        }
    }

    private fun fetchComicsForIds(idsWithThumbnails: Map<Int, Pair<ThumbnailModel, String>>) =
        viewModelScope.launch {
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
