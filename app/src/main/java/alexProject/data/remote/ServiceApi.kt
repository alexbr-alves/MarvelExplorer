package alexProject.data.remote

import alexProject.data.model.event.EventModel
import alexProject.data.model.group.GroupModelResponse
import alexProject.data.model.games.GamesModelResponse
import alexProject.data.model.movies.MoviesModelResponse
import alexProject.data.model.tv_show.TvShowModelResponse
import alexProject.network.response.CharacterResponse
import alexProject.network.response.ComicResponse
import alexProject.network.response.GenericResponse
import alexProject.util.KeyGithub.GITHUB_BASE_URL
import alexProject.util.KeyGithub.RF_MARVEL_GAMES
import alexProject.util.KeyGithub.RF_MARVEL_GROUPS
import alexProject.util.KeyGithub.RF_MARVEL_MOVEIS
import alexProject.util.KeyGithub.RF_MARVEL_TV_SHOW
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    @GET("characters")
    fun getCharacters(
        @Query("nameStartsWith") nameStartsWith: String? = null,
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int? = 0,
    ): Observable<GenericResponse<CharacterResponse>>

    @GET("comics")
    fun comics(
        @Query("titleStartsWith") titleStartsWith: String? = null,
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int? = 0,
        @Query("orderBy") orderBy: String? = null,
    ): Observable<GenericResponse<ComicResponse>>

    @GET("events")
    fun events(
    ): Observable<GenericResponse<EventModel>>

    //Character details

    @GET("characters/{characterId}/comics")
    fun getComicsCharacter(
        @Path(
            value = "characterId",
            encoded = true
        ) characterId: Int,
        @Query("limit") limit: Int? = 100,
        @Query("offset") offset: Int? = 0,
        @Query("orderBy") orderBy: String = "-modified"
    ): Observable<GenericResponse<ComicResponse>>

    @GET("characters/{characterId}/events")
    fun getEventsCharacter(
        @Path(
            value = "characterId",
            encoded = true
        ) characterId: Int,
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int? = 0
    ): Observable<GenericResponse<EventModel>>

    //Comic details

    @GET("comics/{comicId}/characters")
    fun getCharacterComics(
        @Path(
            value = "comicId",
            encoded = true
        ) comicId: Int,
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int? = 0
    ): Observable<GenericResponse<CharacterResponse>>

    @GET("comics/{comicId}/events")
    fun getEventComics(
        @Path(
            value = "comicId",
            encoded = true
        ) comicId: Int,
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int? = 0
    ): Observable<GenericResponse<EventModel>>

    //Event details

    @GET("events/{eventId}/characters")
    fun getCharacterEvent(
        @Path(
            value = "eventId",
            encoded = true
        ) eventId: Int,
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int? = 0
    ): Observable<GenericResponse<CharacterResponse>>

    @GET("events/{eventId}/comics")
    fun getComicEvent(
        @Path(
            value = "eventId",
            encoded = true
        ) eventId: Int,
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int? = 0
    ): Observable<GenericResponse<ComicResponse>>

    // Timeline
    @GET("comics")
    fun getComicTimeline(
        @Query("orderBy") orderBy: String = "-modified",
        @Query("limit") limit: Int = 60,
        @Query("offset") offset: Int = 0,
    ): Observable<GenericResponse<ComicResponse>>

    //GITHUB API
    @GET(GITHUB_BASE_URL + RF_MARVEL_GROUPS)
    fun getGroup(): Observable<GroupModelResponse>

    @GET(GITHUB_BASE_URL + RF_MARVEL_MOVEIS)
    fun getMovies(): Observable<MoviesModelResponse>

    @GET(GITHUB_BASE_URL + RF_MARVEL_TV_SHOW)
    fun getTvShow(): Observable<TvShowModelResponse>

    @GET(GITHUB_BASE_URL + RF_MARVEL_GAMES)
    fun getGames(): Observable<GamesModelResponse>
}