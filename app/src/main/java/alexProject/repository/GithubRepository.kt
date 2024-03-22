package alexProject.repository

import alexProject.data.remote.ServiceApi
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val api: ServiceApi
) {
    suspend fun getGroup() = api.getGroup()
    suspend fun getMovies() = api.getMovies()
    suspend fun getTvShow() = api.getTvShow()
    suspend fun getGames() = api.getGames()
}