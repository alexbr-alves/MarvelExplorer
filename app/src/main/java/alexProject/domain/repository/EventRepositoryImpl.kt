package alexProject.domain.repository

import alexProject.data.model.event.EventModel
import alexProject.data.remote.ServiceApi
import alexProject.data.repository.EventRepository
import alexProject.network.response.GenericResponse
import io.reactivex.Observable
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val service: ServiceApi
) : EventRepository {
    override fun getEventsCharacter(
        characterId: Int,
        limit: Int,
        offset: Int?
    ): Observable<GenericResponse<EventModel>> =
        service.getEventsCharacter(characterId, limit, offset)

    override fun getEvents(): Observable<GenericResponse<EventModel>> =
        service.events()

    override fun getEventComics(
        comicId: Int,
        limit: Int,
        offset: Int?
    ): Observable<GenericResponse<EventModel>> =
        service.getEventComics(comicId, limit, offset)


}