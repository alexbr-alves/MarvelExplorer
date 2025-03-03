package alexProject.domain.useCase.comic

import alexProject.data.repository.CharacterRepository
import alexProject.data.repository.ComicRepository
import alexProject.network.response.CharacterResponse
import alexProject.network.response.ComicResponse
import javax.inject.Inject

class InsertComicDaoUseCase @Inject constructor(
    private val repository: ComicRepository
) {
    operator fun invoke(comic: List<ComicResponse>) = repository.insertComicDao(comic)
}