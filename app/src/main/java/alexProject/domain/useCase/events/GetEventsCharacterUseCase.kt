package alexProject.domain.useCase.events

import alexProject.data.repository.EventRepository
import alexProject.util.toAsyncResult
import javax.inject.Inject

class GetEventsCharacterUseCase @Inject constructor(
    private val repository: EventRepository
) {
    operator fun invoke(characterId: Int, limit: Int, offset: Int?) =
        repository.getEventsCharacter(characterId, limit, offset).toAsyncResult()
}