package alexProject.domain.repository.useCase

import alexProject.data.model.character.CharacterResponse
import alexProject.data.repository.MarvelRepository
import alexProject.network.response.AsyncResult
import alexProject.util.toAsyncResult
import io.reactivex.Observable
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(val repository: MarvelRepository) {
    operator fun invoke(nameStartsWith: String?, limit: Int, offset: Int?): Observable<AsyncResult<CharacterResponse>> {
        return repository.characters(nameStartsWith, limit, offset).toAsyncResult()
    }
}