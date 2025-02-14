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

    override suspend fun events() = api.events()

    override suspend fun getEventsCharacter(characterId: Int, limit: Int, offset: Int?) =
        api.getEventsCharacter(characterId, limit, offset)

    //Comic details

    override suspend fun getEventComics(comicId: Int, limit: Int, offset: Int?) =
        api.getEventComics(comicId, limit, offset)

    //Events details

    //Timeline

}