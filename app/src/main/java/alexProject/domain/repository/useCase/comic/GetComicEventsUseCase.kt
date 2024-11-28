package alexProject.domain.repository.useCase.comic

import alexProject.data.repository.MarvelRepository
import alexProject.network.response.AsyncResult
import alexProject.network.response.CharacterResponse
import alexProject.network.response.ComicModel
import alexProject.network.response.GenericResponse
import alexProject.util.toAsyncResult
import io.reactivex.Observable
import javax.inject.Inject

class GetComicEventsUseCase @Inject constructor(val repository: MarvelRepository) {
    operator fun invoke(
        eventId: Int,
        limit: Int,
        offset: Int? = 0
    ): Observable<AsyncResult<GenericResponse<ComicModel>>> {
        return repository.getComicEvent(eventId, limit, offset).toAsyncResult()
    }
}