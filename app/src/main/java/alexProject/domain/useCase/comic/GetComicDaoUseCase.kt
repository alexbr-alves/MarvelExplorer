package alexProject.domain.useCase.comic

import alexProject.data.repository.CharacterRepository
import alexProject.data.repository.ComicRepository
import javax.inject.Inject

class GetComicDaoUseCase @Inject constructor(
    private val repository: ComicRepository
) {
    operator fun invoke() = repository.getComicDao()
}