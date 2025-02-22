package alexProject.domain.useCase.details

import alexProject.data.repository.DetailsRepository
import javax.inject.Inject

class GetFavoriteUseCase @Inject constructor(
    private val repository: DetailsRepository
) {
    suspend operator fun invoke(characterId: Int) = repository.searchFavorite(characterId)
}