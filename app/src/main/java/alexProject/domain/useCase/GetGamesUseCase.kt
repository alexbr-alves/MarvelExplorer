package alexProject.domain.useCase

import alexProject.data.repository.GithubRepository
import alexProject.util.toAsyncResult
import javax.inject.Inject

class GetGamesUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    operator fun invoke() = repository.getGames().toAsyncResult()
}