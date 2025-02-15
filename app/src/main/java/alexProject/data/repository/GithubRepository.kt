package alexProject.data.repository

import alexProject.data.model.games.GamesModelResponse
import io.reactivex.Observable
import retrofit2.Response


interface GithubRepository {
    fun getGames() : Observable<GamesModelResponse>
}