package alexProject.viewModel.comics

import alexProject.data.SharedPreferences
import alexProject.domain.useCase.comic.GetComicCharactersUseCase
import alexProject.domain.useCase.comic.GetComicDaoUseCase
import alexProject.domain.useCase.comic.GetComicEventsUseCase
import alexProject.domain.useCase.comic.GetComicsUseCase
import alexProject.domain.useCase.comic.InsertComicDaoUseCase
import alexProject.network.response.ComicResponse
import alexProject.util.Constants
import alexProject.viewModel.MarvelViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ComicViewModel @Inject constructor(
    private val getComicsUseCase: GetComicsUseCase,
    private val getComicEventsUseCase: GetComicEventsUseCase,
    private val getComicCharactersUseCase: GetComicCharactersUseCase,
    private val getComicDaoUseCase: GetComicDaoUseCase,
    private val insertComicDaoUseCase: InsertComicDaoUseCase,
    private val sharedPreferences: SharedPreferences
) : MarvelViewModel() {

    private val _comicList = MutableLiveData<List<ComicResponse>>()
    val comicList: LiveData<List<ComicResponse>> get() = _comicList

    private var isRequesting = false
    private var comicLimit = DEFAULT_LIMIT

    fun getComics(
        verify: String? = null,
        id: Int? = null,
        titleStartsWith: String? = null,
        limit: Int = DEFAULT_LIMIT,
        offset: Int
    ) {
        when (verify) {
            Constants.CHARACTER -> {
                getComicsCharacter(id ?: 0, limit, offset)
            }

            Constants.EVENT -> {
                getComimicsEvent(id ?: 0, limit, offset)
            }

            else -> {
                getComics(titleStartsWith)
            }
        }
    }

    private fun getComics(titleStartsWith: String?) {
        if (isRequesting == false) {
            viewModelScope.launch {
                isRequesting = true
                val localComics = withContext(Dispatchers.IO) {
                    getComicDaoUseCase()
                }
                if (localComics.isNotEmpty()) {
                    _comicList.value = filterAvailableThumbnails(localComics)
                    isRequesting = false
                } else if ((sharedPreferences.getLastOffsetComic() + DEFAULT_LIMIT) >= sharedPreferences.getTotalComic()) {
                    comicLimit =
                        sharedPreferences.getLastOffsetComic() + (sharedPreferences.getTotalComic() - sharedPreferences.getLastOffsetComic())
                    requestComic(titleStartsWith, offset = comicLimit)
                } else {
                    requestComic(titleStartsWith, sharedPreferences.getLastOffsetComic())
                }
            }
        }
    }

    private fun requestComic(nameStartsWith: String?, offset: Int) {
        viewModelScope.launch {
            handleObservable(
                observable = getComicsUseCase(
                    nameStartsWith,
                    DEFAULT_LIMIT,
                    sharedPreferences.getLastOffsetComic()
                ),
                { response ->
                    viewModelScope.launch(Dispatchers.IO) {
                        sharedPreferences.saveTotalComic(response.apiResponse.total)
                        val localComic = withContext(Dispatchers.IO) {
                            getComicDaoUseCase()
                        }
                        if (localComic.count() + DEFAULT_LIMIT <= MAX_DATABASE_SIZE) {
                            insertComicDaoUseCase(response.apiResponse.entityList)
                        }
                        sharedPreferences.saveLastOffsetComic(offset + DEFAULT_LIMIT)
                    }
                    _comicList.value =
                        filterAvailableThumbnails(response.apiResponse.entityList)
                    isRequesting = false
                }
            )
        }
    }

    fun loadMoreComics() {
        if (isRequesting == false) {
            isRequesting = true
            requestComic(null, sharedPreferences.getLastOffsetComic())
        }
    }

    fun filterAvailableThumbnails(entityList: List<ComicResponse>): List<ComicResponse> {
        return entityList.filter { comicResponse ->
            comicResponse.thumbnailModel.path != Constants.IMAGE_NOT_AVAILABLE
        }
    }

    private fun getComicsCharacter(id: Int, limit: Int, offset: Int) {
        handleObservable(
            observable = getComicCharactersUseCase(id, limit, offset),
            successHandler = {
                _comicList.value = it.apiResponse.entityList
            }
        )
    }

    private fun getComimicsEvent(id: Int, limit: Int, offset: Int) {
        handleObservable(
            observable = getComicEventsUseCase(id, limit, offset),
            successHandler = {
                _comicList.value = it.apiResponse.entityList
            }
        )
    }

    companion object {
        private const val DEFAULT_LIMIT = 100
        private const val MAX_DATABASE_SIZE = 500
    }
}