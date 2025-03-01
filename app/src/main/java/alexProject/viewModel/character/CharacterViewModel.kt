package alexProject.viewModel.character

import alexProject.data.SharedPreferences
import alexProject.data.local.MarvelDao
import alexProject.domain.useCase.character.GetCharacterComicsUseCase
import alexProject.domain.useCase.character.GetCharacterEventsUseCase
import alexProject.domain.useCase.character.GetCharactersUseCase
import alexProject.network.response.CharacterResponse
import alexProject.util.Constants
import alexProject.viewModel.MarvelViewModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getCharacterEventsUseCase: GetCharacterEventsUseCase,
    private val getCharacterComicsUseCase: GetCharacterComicsUseCase,
    private val marvelDao: MarvelDao,
    private var sharedPreferences: SharedPreferences
) : MarvelViewModel() {

    private val _characterList = MutableLiveData<List<CharacterResponse>>()
    val characterList: LiveData<List<CharacterResponse>> get() = _characterList

    private var isRequesting = false
    private var characterLimit = DEFAULT_LIMIT

    fun fetch(
        verify: String? = null,
        id: Int? = null,
        nameStartsWith: String? = null,
        limit: Int = DEFAULT_LIMIT,
        offset: Int
    ) {
        when (
            verify) {
            Constants.COMIC -> {
                getCharacterComic(id ?: 0, limit, offset)
            }

            Constants.EVENT -> {
                getCharacterEvent(id ?: 0, limit, offset)
            }

            else -> {
                getCharacter(nameStartsWith)
            }
        }
    }

    private fun getCharacter(nameStartsWith: String?) {
        if (isRequesting == false) {
            viewModelScope.launch {
                isRequesting = true
                val localCharacters = withContext(Dispatchers.IO) {
                    marvelDao.getCharacters()
                }

                if (localCharacters.isNotEmpty()) {
                    _characterList.value = filterAvailableThumbnails(localCharacters)
                    isRequesting = false
                } else if ((sharedPreferences.getLastOffset() + DEFAULT_LIMIT) >= sharedPreferences.getTotalCharacter()){
                    characterLimit = sharedPreferences.getLastOffset() + (sharedPreferences.getTotalCharacter() - sharedPreferences.getLastOffset())
                    requestCharacters(nameStartsWith, offset = characterLimit)
                }else {
                    requestCharacters(nameStartsWith, sharedPreferences.getLastOffset())
                }
            }
        }
    }

    fun requestCharacters(nameStartsWith: String?, offset: Int) =
        viewModelScope.launch {
            handleObservable(
                observable = getCharactersUseCase(
                    nameStartsWith,
                    DEFAULT_LIMIT,
                    sharedPreferences.getLastOffset()
                ),
                successHandler = { response ->

                    viewModelScope.launch(Dispatchers.IO) {
                        sharedPreferences.saveTotalCharacter(response.apiResponse.total)
                        val localCharacters = withContext(Dispatchers.IO) {
                            marvelDao.getCharacters()
                        }
                        if (localCharacters.count() + DEFAULT_LIMIT <= MAX_DATABASE_SIZE) {
                            marvelDao.insertCharacter(response.apiResponse.entityList)
                        }
                        sharedPreferences.saveLastOffset(offset + DEFAULT_LIMIT)
                    }

                    _characterList.value =
                        filterAvailableThumbnails(response.apiResponse.entityList)
                    isRequesting = false
                }
            )
        }

    fun refreshScroll() {
        if (isRequesting == false) {
            isRequesting = true
            requestCharacters(null, sharedPreferences.getLastOffset())
        }
    }

    fun filterAvailableThumbnails(entityList: List<CharacterResponse>): List<CharacterResponse> {
        return entityList.filter { characterResponse ->
            characterResponse.thumbnailModel.path != Constants.IMAGE_NOT_AVAILABLE
        }
    }


    private fun getCharacterEvent(characterId: Int, limit: Int, offset: Int) {
        handleObservable(
            observable = getCharacterEventsUseCase(characterId, limit = limit, offset),
            successHandler = {
                _characterList.value = it.apiResponse.entityList
            }
        )
    }

    private fun getCharacterComic(characterId: Int, limit: Int, offset: Int) {
        handleObservable(
            observable = getCharacterComicsUseCase(characterId, limit = limit, offset),
            successHandler = {
                _characterList.value = it.apiResponse.entityList
            }
        )
    }
    companion object {
        private const val DEFAULT_LIMIT = 100
        private const val MAX_DATABASE_SIZE = 500
    }

}