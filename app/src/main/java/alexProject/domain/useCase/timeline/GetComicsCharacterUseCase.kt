package alexProject.domain.useCase.timeline

import alexProject.data.repository.TimelineRepository
import alexProject.network.response.AsyncResult
import alexProject.network.response.ComicResponse
import alexProject.network.response.GenericResponse
import alexProject.util.toAsyncResult
import io.reactivex.Observable
import javax.inject.Inject

class GetComicsCharacterUseCase  @Inject constructor(
    private val repository: TimelineRepository
) {
    operator fun invoke(
        id: Int,
        limit: Int,
        offset: Int? = 0
    ): Observable<AsyncResult<GenericResponse<ComicResponse>>> {
        return repository.getComicsCharacter(id, limit, offset).toAsyncResult()
    }
}