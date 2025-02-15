package alexProject.domain.repository

import alexProject.data.model.games.GamesModelResponse
import alexProject.data.model.tv_show.TvShowModelResponse
import alexProject.data.remote.ServiceApi
import alexProject.data.repository.GithubRepository
import io.reactivex.Observable
import retrofit2.Response
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val api: ServiceApi
) : GithubRepository {
    suspend fun getGroup() = api.getGroup()
    suspend fun getMovies() = api.getMovies()
    override fun getTvShow(): Observable<TvShowModelResponse> = api.getTvShow()
    override fun getGames(): Observable<GamesModelResponse> = api.getGames()
}