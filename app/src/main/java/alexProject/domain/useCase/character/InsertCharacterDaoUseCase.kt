package alexProject.domain.useCase.character

import alexProject.data.repository.CharacterRepository
import alexProject.network.response.CharacterResponse
import javax.inject.Inject

class InsertCharacterDaoUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    operator fun invoke(character: List<CharacterResponse>) = repository.insertCharacterDao(character)
}