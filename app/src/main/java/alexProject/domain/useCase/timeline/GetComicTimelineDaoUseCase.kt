package alexProject.domain.useCase.timeline

import alexProject.data.repository.TimelineRepository
import javax.inject.Inject

class GetComicTimelineDaoUseCase @Inject constructor(
    private val repository: TimelineRepository
) {
    operator fun invoke() = repository.getComicTimelineDao()
}