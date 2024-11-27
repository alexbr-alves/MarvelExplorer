package alexProject.data.repository

import alexProject.data.model.character.CharacterModelResponse
import alexProject.data.model.comic.ComicModelResponse
import alexProject.data.model.event.EventModelResponse
import retrofit2.Response

interface MarvelRepository {
    suspend fun characters(
        nameStartsWith: String? = null,
        limit: Int = 100,
        offset: Int? = 0
    ): Response<CharacterModelResponse>

    suspend fun comics(
        titleStartsWith: String? = null,
        limit: Int = 100,
        offset: Int? = 0,
        orderBy: String? = null
    ): Response<ComicModelResponse>

    suspend fun events(): Response<EventModelResponse>

    suspend fun getComicsCharacter(
        characterId: Int,
        limit: Int? = 100,
        offset: Int? = 0
    ): Response<ComicModelResponse>

    suspend fun getEventsCharacter(
        characterId: Int,
        limit: Int = 100,
        offset: Int? = 0
    ): Response<EventModelResponse>

    suspend fun getCharacterComics(comicId: Int, limit: Int = 100, offset: Int? = 0) : Response<CharacterModelResponse>

    suspend fun getEventComics(comicId: Int, limit: Int = 100, offset: Int? = 0) : Response<EventModelResponse>

    //Events details
    suspend fun getCharacterEvent(eventId: Int, limit: Int = 100, offset: Int? = 0) : Response<CharacterModelResponse>

    suspend fun getComicEvent(eventId: Int, limit: Int = 100, offset: Int? = 0) : Response<ComicModelResponse>

    //Timeline
    suspend fun getComicTimeline() : Response<ComicModelResponse>
}