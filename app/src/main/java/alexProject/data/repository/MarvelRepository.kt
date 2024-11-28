package alexProject.data.repository

import alexProject.network.response.ComicResponse
import alexProject.data.model.event.EventModelResponse
import alexProject.network.response.CharacterResponse
import alexProject.network.response.ComicModel
import alexProject.network.response.GenericResponse
import io.reactivex.Observable
import retrofit2.Response

interface MarvelRepository {
    fun getCharacters(
        nameStartsWith: String? = null,
        limit: Int = 100,
        offset: Int? = 0
    ): Observable<GenericResponse<CharacterResponse>>

    fun getComics(
        titleStartsWith: String? = null,
        limit: Int = 100,
        offset: Int? = 0,
        orderBy: String? = null
    ): Observable<GenericResponse<ComicModel>>

    suspend fun events(): Response<EventModelResponse>

    fun getComicsCharacter(
        characterId: Int,
        limit: Int? = 100,
        offset: Int? = 0
    ): Observable<GenericResponse<ComicModel>>

    suspend fun getEventsCharacter(
        characterId: Int,
        limit: Int = 100,
        offset: Int? = 0
    ): Response<EventModelResponse>

    fun getCharacterComics(comicId: Int, limit: Int = 100, offset: Int? = 0) : Observable<GenericResponse<CharacterResponse>>

    suspend fun getEventComics(comicId: Int, limit: Int = 100, offset: Int? = 0) : Response<EventModelResponse>

    //Events details
    fun getCharacterEvent(eventId: Int, limit: Int = 100, offset: Int? = 0) : Observable<GenericResponse<CharacterResponse>>

    fun getComicEvent(eventId: Int, limit: Int = 100, offset: Int? = 0) : Observable<GenericResponse<ComicModel>>

    //Timeline
    suspend fun getComicTimeline() : Response<ComicResponse>
}