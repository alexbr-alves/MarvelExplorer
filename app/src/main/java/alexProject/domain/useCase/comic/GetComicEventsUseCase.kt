package alexProject.domain.useCase.comic

import alexProject.data.repository.ComicRepository
import alexProject.network.response.AsyncResult
import alexProject.network.response.CharacterResponse
import alexProject.network.response.ComicModel
import alexProject.network.response.GenericResponse
import alexProject.util.toAsyncResult
import io.reactivex.Observable
import javax.inject.Inject

class GetComicEventsUseCase @Inject constructor(val repository: ComicRepository) {
    operator fun invoke(
        eventId: Int,
        limit: Int,
        offset: Int? = 0
    ): Observable<AsyncResult<GenericResponse<ComicModel>>> {
        return repository.getComicEvent(eventId, limit, offset).toAsyncResult()
    }
}