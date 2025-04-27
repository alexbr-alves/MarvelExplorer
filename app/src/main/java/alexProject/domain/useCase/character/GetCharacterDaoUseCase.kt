package alexProject.domain.useCase.character

import alexProject.data.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterDaoUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    operator fun invoke() = repository.getCharacterDao()
}