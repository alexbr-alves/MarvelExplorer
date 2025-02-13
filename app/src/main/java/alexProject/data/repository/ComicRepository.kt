package alexProject.data.repository

import alexProject.network.response.ComicModel
import alexProject.network.response.GenericResponse
import io.reactivex.Observable

interface ComicRepository {
    fun getComics(
        titleStartsWith: String? = null,
        limit: Int = 100,
        offset: Int? = 0,
        orderBy: String? = null
    ): Observable<GenericResponse<ComicModel>>

    fun getComicEvent(
        eventId: Int,
        limit: Int = 100,
        offset: Int? = 0
    ): Observable<GenericResponse<ComicModel>>

    fun getComicsCharacter(
        characterId: Int,
        limit: Int? = 100,
        offset: Int? = 0
    ): Observable<GenericResponse<ComicModel>>
}