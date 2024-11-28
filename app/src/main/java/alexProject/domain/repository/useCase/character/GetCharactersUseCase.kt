package alexProject.domain.repository.useCase.character

import alexProject.data.repository.MarvelRepository
import alexProject.network.response.AsyncResult
import alexProject.network.response.CharacterResponse
import alexProject.network.response.GenericResponse
import alexProject.util.toAsyncResult
import io.reactivex.Observable
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(val repository: MarvelRepository) {
    operator fun invoke(
        nameStartsWith: String?,
        limit: Int,
        offset: Int?
    ): Observable<AsyncResult<GenericResponse<CharacterResponse>>> {
        return repository.getCharacters(nameStartsWith, limit, offset).toAsyncResult()
    }
}