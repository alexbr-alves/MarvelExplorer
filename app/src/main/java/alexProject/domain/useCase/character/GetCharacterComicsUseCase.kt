package alexProject.domain.useCase.character

import alexProject.data.repository.CharacterRepository
import alexProject.network.response.AsyncResult
import alexProject.network.response.CharacterResponse
import alexProject.network.response.GenericResponse
import alexProject.util.toAsyncResult
import io.reactivex.Observable
import javax.inject.Inject

class GetCharacterComicsUseCase @Inject constructor(val repository: CharacterRepository) {
    operator fun invoke(
        comicId: Int,
        limit: Int,
        offset: Int? = 0
    ): Observable<AsyncResult<GenericResponse<CharacterResponse>>> {
        return repository.getCharacterComics(comicId, limit, offset).toAsyncResult()
    }
}