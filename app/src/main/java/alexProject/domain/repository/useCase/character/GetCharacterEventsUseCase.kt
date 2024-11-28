package alexProject.domain.repository.useCase.character

import alexProject.data.repository.MarvelRepository
import alexProject.network.response.AsyncResult
import alexProject.network.response.CharacterResponse
import alexProject.network.response.GenericResponse
import alexProject.util.toAsyncResult
import io.reactivex.Observable
import javax.inject.Inject

class GetCharacterEventsUseCase @Inject constructor(val repository: MarvelRepository) {
    operator fun invoke(
        eventId: Int,
        limit: Int,
        offset: Int? = 0
    ): Observable<AsyncResult<GenericResponse<CharacterResponse>>> {
        return repository.getCharacterEvent(eventId, limit, offset).toAsyncResult()
    }
}