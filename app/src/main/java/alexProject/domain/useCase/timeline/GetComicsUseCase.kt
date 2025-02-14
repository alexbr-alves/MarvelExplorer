package alexProject.domain.useCase.timeline

import alexProject.data.repository.TimelineRepository
import alexProject.util.toAsyncResult
import javax.inject.Inject

class GetComicsUseCase @Inject constructor(
    private val repository: TimelineRepository
) {
    operator fun invoke() = repository.getComics().toAsyncResult()
}