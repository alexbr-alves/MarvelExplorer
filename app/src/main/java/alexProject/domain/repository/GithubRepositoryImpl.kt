package alexProject.domain.repository

import alexProject.data.model.games.GamesModelResponse
import alexProject.data.model.group.GroupModelResponse
import alexProject.data.model.movies.MoviesModelResponse
import alexProject.data.model.tv_show.TvShowModelResponse
import alexProject.data.remote.ServiceApi
import alexProject.data.repository.GithubRepository
import io.reactivex.Observable
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val api: ServiceApi
) : GithubRepository {
    override fun getGroups(): Observable<GroupModelResponse> = api.getGroup()
    override fun getMovies(): Observable<MoviesModelResponse> = api.getMovies()
    override fun getTvShow(): Observable<TvShowModelResponse> = api.getTvShow()
    override fun getGames(): Observable<GamesModelResponse> = api.getGames()
}