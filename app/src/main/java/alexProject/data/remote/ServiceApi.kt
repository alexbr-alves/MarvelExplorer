package alexProject.data.remote

import alexProject.data.model.group.GroupModelResponse
import alexProject.data.model.character.CharacterModelResponse
import alexProject.data.model.comic.ComicModelResponse
import alexProject.data.model.event.EventModelResponse
import alexProject.data.model.games.GamesModelResponse
import alexProject.data.model.movies.MoviesModelResponse
import alexProject.data.model.tv_show.TvShowModelResponse
import alexProject.util.KeyGithub.GITHUB_BASE_URL
import alexProject.util.KeyGithub.RF_MARVEL_GAMES
import alexProject.util.KeyGithub.RF_MARVEL_GROUPS
import alexProject.util.KeyGithub.RF_MARVEL_MOVEIS
import alexProject.util.KeyGithub.RF_MARVEL_TV_SHOW
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    @GET("characters")
    suspend fun characters(
        @Query("nameStartsWith") nameStartsWith : String? = null,
        @Query("limit") limit : Int = 30,
        @Query("offset") offset : Int? = 0,
    ) : Response<CharacterModelResponse>

    @GET("comics")
    suspend fun comics(
        @Query("titleStartsWith") titleStartsWith : String? = null,
        @Query("limit") limit : Int = 30,
        @Query("offset") offset : Int? = 0,
        @Query("orderBy") orderBy : String? = null,
    ) : Response<ComicModelResponse>

    @GET("events")
    suspend fun events(
    ) : Response<EventModelResponse>

    //Character details

    @GET("characters/{characterId}/comics")
    suspend fun getComicsCharacter(
        @Path(
            value = "characterId",
            encoded = true
        ) characterId : Int,
        @Query("limit") limit : Int? = 30,
        @Query("offset") offset : Int? = 0,
        @Query("orderBy") orderBy : String = "-modified"
    ) : Response<ComicModelResponse>

    @GET("characters/{characterId}/events")
    suspend fun getEventsCharacter(
        @Path(
            value = "characterId",
            encoded = true
        ) characterId : Int,
        @Query("limit") limit : Int = 30,
        @Query("offset") offset : Int? = 0
    ) : Response<EventModelResponse>

    //Comic details

    @GET("comics/{comicId}/characters")
    suspend fun getCharacterComics(
        @Path(
            value = "comicId",
            encoded = true
        ) comicId : Int,
        @Query("limit") limit : Int = 30,
        @Query("offset") offset : Int? = 0
    ) : Response<CharacterModelResponse>

    @GET("comics/{comicId}/events")
    suspend fun getEventComics(
        @Path(
            value = "comicId",
            encoded = true
        ) comicId : Int,
        @Query("limit") limit : Int = 30,
        @Query("offset") offset : Int? = 0
    ) : Response<EventModelResponse>

    //Event details

    @GET("events/{eventId}/characters")
    suspend fun getCharacterEvent(
        @Path(
            value = "eventId",
            encoded = true
        ) eventId : Int,
        @Query("limit") limit : Int = 30,
        @Query("offset") offset : Int? = 0
    ) : Response<CharacterModelResponse>

    @GET("events/{eventId}/comics")
    suspend fun getComicEvent(
        @Path(
            value = "eventId",
            encoded = true
        ) eventId : Int,
        @Query("limit") limit : Int = 30,
        @Query("offset") offset : Int? = 0
    ) : Response<ComicModelResponse>

    // Timeline
    @GET("comics")
    suspend fun getComicTimeline(
        @Query("orderBy") orderBy : String? = "-modified",
        @Query("limit") limit : Int?  = 60,
        @Query("offset") offset : Int? = 0,
    ) : Response<ComicModelResponse>

    //GITHUB API
    @GET(GITHUB_BASE_URL + RF_MARVEL_GROUPS)
    suspend fun getGroup() : Response<GroupModelResponse>

    @GET(GITHUB_BASE_URL + RF_MARVEL_MOVEIS)
    suspend fun getMovies() : Response<MoviesModelResponse>

    @GET(GITHUB_BASE_URL + RF_MARVEL_TV_SHOW)
    suspend fun getTvShow() : Response<TvShowModelResponse>

    @GET(GITHUB_BASE_URL + RF_MARVEL_GAMES)
    suspend fun getGames() : Response<GamesModelResponse>
}