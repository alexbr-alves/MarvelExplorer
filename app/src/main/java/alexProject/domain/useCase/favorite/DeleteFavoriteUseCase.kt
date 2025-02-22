package alexProject.domain.useCase.favorite

import alexProject.data.model.FavoriteModel
import alexProject.data.repository.FavoriteRepository
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator suspend fun invoke(favorite : FavoriteModel) = repository.delete(favorite)
}