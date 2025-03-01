package alexProject.domain.useCase.events

import alexProject.data.repository.EventRepository
import alexProject.util.toAsyncResult
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val repository: EventRepository
) {
    operator fun invoke() = repository.getEvents().toAsyncResult()
}