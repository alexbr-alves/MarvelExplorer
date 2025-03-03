package alexProject.viewModel.character

import alexProject.data.SharedPreferences
import alexProject.domain.useCase.character.GetCharacterComicsUseCase
import alexProject.domain.useCase.character.GetCharacterDaoUseCase
import alexProject.domain.useCase.character.GetCharacterEventsUseCase
import alexProject.domain.useCase.character.GetCharactersUseCase
import alexProject.domain.useCase.character.InsertCharacterDaoUseCase
import alexProject.network.response.CharacterResponse
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
class CharacterViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getCharacterEventsUseCase: GetCharacterEventsUseCase,
    private val getCharacterComicsUseCase: GetCharacterComicsUseCase,
    private val InsertCharacterDaoUseCase: InsertCharacterDaoUseCase,
    private val GetCharacterDaoUseCase: GetCharacterDaoUseCase,
    private var sharedPreferences: SharedPreferences
) : MarvelViewModel() {

    private val _characterList = MutableLiveData<List<CharacterResponse>>()
    val characterList: LiveData<List<CharacterResponse>> get() = _characterList

    private var isRequesting = false
    private var characterLimit = DEFAULT_LIMIT

    fun getCharacter(
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

    fun getCharactersByName(query: String) {
        handleObservable(
            observable = getCharactersUseCase(nameStartsWith = query, limit = DEFAULT_LIMIT, 0),
            successHandler = {
                _characterList.value = emptyList()
                _characterList.value = it.apiResponse.entityList
            }
        )
    }

    fun loadMoreCharacters() {
        if (isRequesting == false) {
            isRequesting = true
            requestCharacters(null, sharedPreferences.getLastOffsetCharacter())
        }
    }

    private fun getCharacter(nameStartsWith: String?) {
        if (isRequesting == false) {
            viewModelScope.launch {
                isRequesting = true
                val localCharacters = withContext(Dispatchers.IO) {
                    GetCharacterDaoUseCase()
                }

                if (localCharacters.isNotEmpty()) {
                    _characterList.value = filterAvailableThumbnails(localCharacters)
                    isRequesting = false
                } else if ((sharedPreferences.getLastOffsetCharacter() + DEFAULT_LIMIT) >= sharedPreferences.getTotalCharacter()) {
                    characterLimit =
                        sharedPreferences.getLastOffsetCharacter() + (sharedPreferences.getTotalCharacter() - sharedPreferences.getLastOffsetCharacter())
                    requestCharacters(nameStartsWith, offset = characterLimit)
                } else {
                    requestCharacters(nameStartsWith, sharedPreferences.getLastOffsetCharacter())
                }
            }
        }
    }

    private fun requestCharacters(nameStartsWith: String?, offset: Int) =
        viewModelScope.launch {
            handleObservable(
                observable = getCharactersUseCase(
                    nameStartsWith,
                    DEFAULT_LIMIT,
                    sharedPreferences.getLastOffsetCharacter()
                ),
                successHandler = { response ->

                    viewModelScope.launch(Dispatchers.IO) {
                        sharedPreferences.saveTotalCharacter(response.apiResponse.total)
                        val localCharacters = withContext(Dispatchers.IO) {
                            GetCharacterDaoUseCase()
                        }
                        if (localCharacters.count() + DEFAULT_LIMIT <= MAX_DATABASE_SIZE) {
                            InsertCharacterDaoUseCase(response.apiResponse.entityList)
                        }
                        sharedPreferences.saveLastOffsetCharacter(offset + DEFAULT_LIMIT)
                    }

                    _characterList.value =
                        filterAvailableThumbnails(response.apiResponse.entityList)
                    isRequesting = false
                }
            )
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