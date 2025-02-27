package alexProject.domain.useCase.events

import alexProject.data.repository.EventRepository
import alexProject.util.toAsyncResult
import javax.inject.Inject

class GetEventsComicsUseCase @Inject constructor(
    private val repository: EventRepository
) {
    operator fun invoke(comicId: Int, limit: Int, offset: Int?) =
        repository.getEventComics(comicId, limit, offset).toAsyncResult()
}