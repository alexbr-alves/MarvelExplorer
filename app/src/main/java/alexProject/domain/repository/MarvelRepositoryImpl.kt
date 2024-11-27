package alexProject.domain.repository

import alexProject.data.remote.ServiceApi
import alexProject.data.repository.MarvelRepository
import javax.inject.Inject

class MarvelRepositoryImpl @Inject constructor(
    private val api: ServiceApi
): MarvelRepository {
    override suspend fun characters(nameStartsWith: String?, limit: Int, offset: Int?) =
        api.characters(nameStartsWith, limit, offset)

    override suspend fun comics(
        titleStartsWith: String?,
        limit: Int,
        offset: Int?,
        orderBy: String?
    ) = api.comics(titleStartsWith, limit, offset, orderBy)

    override suspend fun events() = api.events()

    //Character details
    override suspend fun getComicsCharacter(characterId: Int, limit: Int?, offset: Int?) =
        api.getComicsCharacter(characterId, limit, offset)

    override suspend fun getEventsCharacter(characterId: Int, limit: Int, offset: Int?) =
        api.getEventsCharacter(characterId, limit, offset)

    //Comic details
    override suspend fun getCharacterComics(comicId: Int, limit: Int, offset: Int?) =
        api.getCharacterComics(comicId, limit, offset)

    override suspend fun getEventComics(comicId: Int, limit: Int, offset: Int?) =
        api.getEventComics(comicId, limit, offset)

    //Events details
    override suspend fun getCharacterEvent(eventId: Int, limit: Int, offset: Int?) =
        api.getCharacterEvent(eventId, limit, offset)

    override suspend fun getComicEvent(eventId: Int, limit: Int, offset: Int?) =
        api.getComicEvent(eventId, limit, offset)

    //Timeline
    override suspend fun getComicTimeline() = api.getComicTimeline()

}