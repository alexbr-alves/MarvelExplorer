package alexProject.domain.useCase.comic

import alexProject.data.repository.ComicRepository
import alexProject.network.response.AsyncResult
import alexProject.network.response.ComicResponse
import alexProject.network.response.GenericResponse
import alexProject.util.toAsyncResult
import io.reactivex.Observable
import javax.inject.Inject

class GetComicCharactersUseCase @Inject constructor(val repository: ComicRepository) {
    operator fun invoke(
        eventId: Int,
        limit: Int,
        offset: Int? = 0
    ): Observable<AsyncResult<GenericResponse<ComicResponse>>> {
        return repository.getComicsCharacter(eventId, limit, offset).toAsyncResult()
    }
}