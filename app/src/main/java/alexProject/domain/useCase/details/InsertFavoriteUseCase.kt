package alexProject.domain.useCase.details

import alexProject.data.model.FavoriteModel
import alexProject.data.repository.DetailsRepository
import javax.inject.Inject

class InsertFavoriteUseCase @Inject constructor(
    val repository: DetailsRepository
) {
    suspend operator fun invoke(favorite: FavoriteModel) = repository.insert(favorite)
}