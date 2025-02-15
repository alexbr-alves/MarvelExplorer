package alexProject.data.repository

import alexProject.data.model.games.GamesModelResponse
import alexProject.data.model.tv_show.TvShowModelResponse
import io.reactivex.Observable
import retrofit2.Response

interface GithubRepository {
    fun getGames(): Observable<GamesModelResponse>
    fun getTvShow(): Observable<TvShowModelResponse>
}