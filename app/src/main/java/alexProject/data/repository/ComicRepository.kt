package alexProject.data.repository

import alexProject.network.response.CharacterResponse
import alexProject.network.response.ComicResponse
import alexProject.network.response.GenericResponse
import io.reactivex.Observable

interface ComicRepository {
    fun getComics(
        titleStartsWith: String? = null,
        limit: Int = 100,
        offset: Int? = 0,
        orderBy: String? = null
    ): Observable<GenericResponse<ComicResponse>>

    fun getComicEvent(
        eventId: Int,
        limit: Int = 100,
        offset: Int? = 0
    ): Observable<GenericResponse<ComicResponse>>

    fun getComicsCharacter(
        characterId: Int,
        limit: Int? = 100,
        offset: Int? = 0
    ): Observable<GenericResponse<ComicResponse>>

    fun getComicDao(): List<ComicResponse>

    fun insertComicDao(comic: List<ComicResponse>): Unit
}