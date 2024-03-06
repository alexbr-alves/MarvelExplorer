package alexProject.data.remote

import alexProject.data.model.character.CharacterModelResponse
import alexProject.data.model.comic.ComicModelResponse
import alexProject.data.model.event.EventModelResponse
import alexProject.data.model.timeline.TimelineModelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    @GET("characters")
    suspend fun characters(
        @Query("nameStartsWith") nameStartsWith : String? = null,
        @Query("limit") limit : Int = 100,
        @Query("offset") offset : Int? = 0,
    ) : Response<CharacterModelResponse>

    @GET("comics")
    suspend fun comics(
        @Query("titleStartsWith") titleStartsWith : String? = null,
        @Query("limit") limit : Int = 100,
        @Query("offset") offset : Int? = 0,
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
        @Query("limit") limit : Int = 100,
        @Query("offset") offset : Int? = 0
    ) : Response<ComicModelResponse>

    @GET("characters/{characterId}/events")
    suspend fun getEventsCharacter(
        @Path(
            value = "characterId",
            encoded = true
        ) characterId : Int,
        @Query("limit") limit : Int = 100,
        @Query("offset") offset : Int? = 0
    ) : Response<EventModelResponse>

    //Comic details

    @GET("comics/{comicId}/characters")
    suspend fun getCharacterComics(
        @Path(
            value = "comicId",
            encoded = true
        ) comicId : Int,
        @Query("limit") limit : Int = 100,
        @Query("offset") offset : Int? = 0
    ) : Response<CharacterModelResponse>

    @GET("comics/{comicId}/events")
    suspend fun getEventComics(
        @Path(
            value = "comicId",
            encoded = true
        ) comicId : Int,
        @Query("limit") limit : Int = 100,
        @Query("offset") offset : Int? = 0
    ) : Response<EventModelResponse>

    //Event details

    @GET("events/{eventId}/characters")
    suspend fun getCharacterEvent(
        @Path(
            value = "eventId",
            encoded = true
        ) eventId : Int,
        @Query("limit") limit : Int = 100,
        @Query("offset") offset : Int? = 0
    ) : Response<CharacterModelResponse>

    @GET("events/{eventId}/comics")
    suspend fun getComicEvent(
        @Path(
            value = "eventId",
            encoded = true
        ) eventId : Int,
        @Query("limit") limit : Int = 100,
        @Query("offset") offset : Int? = 0
    ) : Response<ComicModelResponse>

    // Timeline
    @GET("characters")
    suspend fun getCharacterTimeline(
        @Query("orderBy") orderBy : String = "-modified",
        @Query("limit") limit : Int = 20
    ) : Response<TimelineModelResponse>

    @GET("comics")
    suspend fun getComicTimeline(
        @Query("orderBy") orderBy : String? = "-modified",
        @Query("limit") limit : Int?  = 20,
        @Query("offset") offset : Int? = 0,
    ) : Response<TimelineModelResponse>

    @GET("events")
    suspend fun getEventTimeline(
        @Query("orderBy") orderBy : String = "-modified",
        @Query("limit") limit : Int = 20
    ) : Response<TimelineModelResponse>

}