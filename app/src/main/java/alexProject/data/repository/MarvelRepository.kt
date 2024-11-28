package alexProject.data.repository

import alexProject.network.response.ComicResponse
import alexProject.data.model.event.EventModelResponse
import alexProject.network.response.CharacterResponse
import alexProject.network.response.GenericResponse
import io.reactivex.Observable
import retrofit2.Response

interface MarvelRepository {
    fun getCharacters(
        nameStartsWith: String? = null,
        limit: Int = 100,
        offset: Int? = 0
    ): Observable<GenericResponse<CharacterResponse>>

    suspend fun comics(
        titleStartsWith: String? = null,
        limit: Int = 100,
        offset: Int? = 0,
        orderBy: String? = null
    ): Response<ComicResponse>

    suspend fun events(): Response<EventModelResponse>

    suspend fun getComicsCharacter(
        characterId: Int,
        limit: Int? = 100,
        offset: Int? = 0
    ): Response<ComicResponse>

    suspend fun getEventsCharacter(
        characterId: Int,
        limit: Int = 100,
        offset: Int? = 0
    ): Response<EventModelResponse>

    fun getCharacterComics(comicId: Int, limit: Int = 100, offset: Int? = 0) : Observable<GenericResponse<CharacterResponse>>

    suspend fun getEventComics(comicId: Int, limit: Int = 100, offset: Int? = 0) : Response<EventModelResponse>

    //Events details
    fun getCharacterEvent(eventId: Int, limit: Int = 100, offset: Int? = 0) : Observable<GenericResponse<CharacterResponse>>

    suspend fun getComicEvent(eventId: Int, limit: Int = 100, offset: Int? = 0) : Response<ComicResponse>

    //Timeline
    suspend fun getComicTimeline() : Response<ComicResponse>
}