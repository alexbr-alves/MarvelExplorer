package alexProject.domain.useCase.timeline

import alexProject.data.repository.TimelineRepository
import alexProject.domain.repository.DatabaseRepository
import alexProject.util.toAsyncResult
import javax.inject.Inject

class GetStoryUseCase @Inject constructor(private val repository : TimelineRepository) {
    operator suspend fun invoke () = repository.getStory()
}