package alexProject.domain.useCase.timeline

import alexProject.data.repository.ComicRepository
import alexProject.data.repository.TimelineRepository
import alexProject.network.response.ComicResponse
import javax.inject.Inject

class InsertComicTimelineDaoUseCase @Inject constructor(
    private val repository: TimelineRepository
) {
    operator fun invoke(comic: List<ComicResponse>) = repository.insertComicTimelineDao(comic)
}