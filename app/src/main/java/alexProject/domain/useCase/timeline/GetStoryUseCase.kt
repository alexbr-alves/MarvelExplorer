package alexProject.domain.useCase.timeline

import alexProject.data.model.FavoriteModel
import alexProject.data.repository.TimelineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStoryUseCase @Inject constructor(private val repository : TimelineRepository) {
    operator fun invoke () : Flow<List<FavoriteModel>> = repository.getStory()
}