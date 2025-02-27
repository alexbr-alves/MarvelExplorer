package alexProject.domain.useCase.comic

import alexProject.data.repository.ComicRepository
import alexProject.network.response.AsyncResult
import alexProject.network.response.ComicModel
import alexProject.network.response.GenericResponse
import alexProject.util.toAsyncResult
import io.reactivex.Observable
import javax.inject.Inject

class GetComicsUseCase @Inject constructor(val repository: ComicRepository) {
    operator fun invoke(
        nameStartsWith: String?,
        limit: Int,
        offset: Int?
    ): Observable<AsyncResult<GenericResponse<ComicModel>>> {
        return repository.getComics(nameStartsWith, limit, offset).toAsyncResult()
    }
}