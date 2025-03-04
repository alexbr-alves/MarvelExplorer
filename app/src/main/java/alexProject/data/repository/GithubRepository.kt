package alexProject.data.repository

import alexProject.data.model.games.GamesModelResponse
import alexProject.data.model.group.GroupModelResponse
import alexProject.data.model.movies.MoviesModelResponse
import alexProject.data.model.tv_show.TvShowModelResponse
import io.reactivex.Observable

interface GithubRepository {
    fun getGames(): Observable<GamesModelResponse>
    fun getTvShow(): Observable<TvShowModelResponse>
    fun getMovies(): Observable<MoviesModelResponse>
    fun getGroups(): Observable<GroupModelResponse>
}