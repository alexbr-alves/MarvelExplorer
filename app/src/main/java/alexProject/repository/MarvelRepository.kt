package alexProject.repository

import alexProject.data.local.MarvelDao
import alexProject.data.model.FavoriteModel
import alexProject.data.remote.ServiceApi
import javax.inject.Inject

class MarvelRepository @Inject constructor(
    private val api : ServiceApi,
    private val dao : MarvelDao
) {
    suspend fun characters(nameStartsWith : String? = null, limit: Int = 100, offset : Int? = 0) = api.characters(nameStartsWith, limit,  offset)
    suspend fun comics(titleStartsWith : String? = null, limit: Int = 100, offset : Int? = 0) = api.comics(titleStartsWith, limit, offset)
    suspend fun events(nameStartsWith : String? = null) = api.events(nameStartsWith)

    //Character details
    suspend fun getComicsCharacter(characterId : Int, limit: Int = 100, offset : Int? = 0) = api.getComicsCharacter(characterId, limit, offset)
    suspend fun getEventsCharacter(characterId : Int) = api.getEventsCharacter(characterId)

    //Comic details
    suspend fun getCharacterComics(comicId : Int) = api.getCharacterComics(comicId)
    suspend fun getEventComics(comicId : Int) = api.getEventComics(comicId)

    //Events details
    suspend fun getCharacterEvent(eventId : Int) = api.getCharacterEvent(eventId)
    suspend fun getComicEvent(eventId : Int) = api.getComicEvent(eventId)

    //Timeline
    suspend fun getCharacterTimeline() = api.getCharacterTimeline()
    suspend fun getComicTimeline() = api.getComicTimeline()
    suspend fun getEventTimeline() = api.getEventTimeline()

    //DAO
    suspend fun insert(characterModel : FavoriteModel) = dao.insert(characterModel)
    fun getAll() = dao.getAll()
    suspend fun delete(characterModel : FavoriteModel) = dao.delete(characterModel)
    suspend fun searchFavorite(id : Int) = dao.searchFavorite(id)
}