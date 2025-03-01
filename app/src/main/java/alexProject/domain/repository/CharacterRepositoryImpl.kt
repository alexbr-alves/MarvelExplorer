package alexProject.domain.repository

import alexProject.data.local.MarvelDao
import alexProject.data.remote.ServiceApi
import alexProject.data.repository.CharacterRepository
import alexProject.network.response.CharacterResponse
import alexProject.network.response.GenericResponse
import io.reactivex.Observable
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: ServiceApi,
    private val dao: MarvelDao
) : CharacterRepository {

    override fun getCharacters(
        nameStartsWith: String?,
        limit: Int,
        offset: Int?
    ): Observable<GenericResponse<CharacterResponse>> =
        api.getCharacters(nameStartsWith, limit, offset)

    override fun getCharacterEvent(eventId: Int, limit: Int, offset: Int?) =
        api.getCharacterEvent(eventId, limit, offset)

    override fun getCharacterComics(comicId: Int, limit: Int, offset: Int?) =
        api.getCharacterComics(comicId, limit, offset)

    override fun getCharacterDao(): List<CharacterResponse> =
        dao.getCharacters()

    override fun insertCharacterDao(character: List<CharacterResponse>): Unit =
        dao.insertCharacter(character)

}