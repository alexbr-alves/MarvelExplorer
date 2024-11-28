package alexProject.domain.repository

import alexProject.data.remote.ServiceApi
import alexProject.data.repository.MarvelRepository
import alexProject.network.response.CharacterResponse
import alexProject.network.response.GenericResponse
import io.reactivex.Observable
import javax.inject.Inject

class MarvelRepositoryImpl @Inject constructor(
    private val api: ServiceApi
) : MarvelRepository {
    override fun getCharacters(
        nameStartsWith: String?,
        limit: Int,
        offset: Int?
    ): Observable<GenericResponse<CharacterResponse>> =
        api.getCharacters(nameStartsWith, limit, offset)

    override fun getComics(
        titleStartsWith: String?,
        limit: Int,
        offset: Int?,
        orderBy: String?
    ) = api.comics(titleStartsWith, limit, offset, orderBy)

    override suspend fun events() = api.events()

    //Character details
    override fun getComicsCharacter(characterId: Int, limit: Int?, offset: Int?) =
        api.getComicsCharacter(characterId, limit, offset)

    override suspend fun getEventsCharacter(characterId: Int, limit: Int, offset: Int?) =
        api.getEventsCharacter(characterId, limit, offset)

    //Comic details
    override fun getCharacterComics(comicId: Int, limit: Int, offset: Int?) =
        api.getCharacterComics(comicId, limit, offset)

    override suspend fun getEventComics(comicId: Int, limit: Int, offset: Int?) =
        api.getEventComics(comicId, limit, offset)

    //Events details
    override fun getCharacterEvent(eventId: Int, limit: Int, offset: Int?) =
        api.getCharacterEvent(eventId, limit, offset)

    override fun getComicEvent(eventId: Int, limit: Int, offset: Int?) =
        api.getComicEvent(eventId, limit, offset)

    //Timeline
    override suspend fun getComicTimeline() = api.getComicTimeline()

}