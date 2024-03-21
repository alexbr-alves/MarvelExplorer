package alexProject.repository

import alexProject.data.local.MarvelDao
import alexProject.data.model.FavoriteModel
import alexProject.data.remote.ServiceApi
import javax.inject.Inject

class MarvelRepository @Inject constructor(
    private val api: ServiceApi,
    private val dao: MarvelDao
) {
    suspend fun characters(nameStartsWith: String? = null, limit: Int = 100, offset: Int? = 0) =
        api.characters(nameStartsWith, limit, offset)

    suspend fun comics(
        titleStartsWith: String? = null,
        limit: Int = 100,
        offset: Int? = 0,
        orderBy: String? = null
    ) = api.comics(titleStartsWith, limit, offset, orderBy)

    suspend fun events() = api.events()

    //Character details
    suspend fun getComicsCharacter(characterId: Int, limit: Int = 100, offset: Int? = 0) =
        api.getComicsCharacter(characterId, limit, offset)

    suspend fun getEventsCharacter(characterId: Int, limit: Int = 100, offset: Int? = 0) =
        api.getEventsCharacter(characterId, limit, offset)

    //Comic details
    suspend fun getCharacterComics(comicId: Int, limit: Int = 100, offset: Int? = 0) =
        api.getCharacterComics(comicId, limit, offset)

    suspend fun getEventComics(comicId: Int, limit: Int = 100, offset: Int? = 0) =
        api.getEventComics(comicId, limit, offset)

    //Events details
    suspend fun getCharacterEvent(eventId: Int, limit: Int = 100, offset: Int? = 0) =
        api.getCharacterEvent(eventId, limit, offset)

    suspend fun getComicEvent(eventId: Int, limit: Int = 100, offset: Int? = 0) =
        api.getComicEvent(eventId, limit, offset)

    //Timeline
    suspend fun getComicTimeline() = api.getComicTimeline()

    //DAO
    suspend fun insert(characterModel: FavoriteModel) = dao.insert(characterModel)
    fun getAll() = dao.getAll()
    suspend fun delete(characterModel: FavoriteModel) = dao.delete(characterModel)
    suspend fun searchFavorite(id: Int) = dao.searchFavorite(id)

    suspend fun getGroup() = api.getGroup()
    suspend fun getMovies() = api.getMovies()
    suspend fun getTvShow() = api.getTvShow()
    suspend fun getGames() = api.getGames()
}