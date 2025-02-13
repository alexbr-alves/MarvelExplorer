package alexProject.data.repository

import alexProject.network.response.CharacterResponse
import alexProject.network.response.GenericResponse
import io.reactivex.Observable

interface CharacterRepository {
    fun getCharacters(
        nameStartsWith: String? = null,
        limit: Int = 100,
        offset: Int? = 0
    ): Observable<GenericResponse<CharacterResponse>>

    fun getCharacterEvent(eventId: Int, limit: Int = 100, offset: Int? = 0) : Observable<GenericResponse<CharacterResponse>>

    fun getCharacterComics(comicId: Int, limit: Int = 100, offset: Int? = 0) : Observable<GenericResponse<CharacterResponse>>

}