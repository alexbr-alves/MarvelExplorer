package alexProject.data.repository

import alexProject.data.model.event.EventModel
import alexProject.network.response.GenericResponse
import io.reactivex.Observable

interface EventRepository {
    fun getEventsCharacter(
        characterId: Int,
        limit: Int,
        offset: Int?
    ): Observable<GenericResponse<EventModel>>

    fun getEvents(): Observable<GenericResponse<EventModel>>

    fun getEventComics(comicId: Int, limit: Int, offset: Int?): Observable<GenericResponse<EventModel>>
}