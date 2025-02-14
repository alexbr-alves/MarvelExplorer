package alexProject.data.repository

import alexProject.network.response.ComicResponse
import alexProject.data.model.event.EventModelResponse
import alexProject.network.response.CharacterResponse
import alexProject.network.response.ComicModel
import alexProject.network.response.GenericResponse
import io.reactivex.Observable
import retrofit2.Response

interface MarvelRepository {


    suspend fun events(): Response<EventModelResponse>

    suspend fun getEventsCharacter(
        characterId: Int,
        limit: Int = 100,
        offset: Int? = 0
    ): Response<EventModelResponse>


    suspend fun getEventComics(comicId: Int, limit: Int = 100, offset: Int? = 0) : Response<EventModelResponse>

    //Events details

    //Timeline
}