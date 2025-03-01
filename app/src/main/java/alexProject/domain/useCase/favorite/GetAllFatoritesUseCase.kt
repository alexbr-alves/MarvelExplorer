package alexProject.domain.useCase.favorite

import alexProject.data.repository.FavoriteRepository
import javax.inject.Inject

class GetAllFatoritesUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke() = repository.getAll()
}