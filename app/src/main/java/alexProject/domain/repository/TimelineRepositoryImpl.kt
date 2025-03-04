package alexProject.domain.repository

import alexProject.data.local.MarvelDao
import alexProject.data.model.FavoriteModel
import alexProject.data.remote.ServiceApi
import alexProject.data.repository.TimelineRepository
import alexProject.network.response.ComicResponse
import alexProject.network.response.ComicTimelineResponse
import alexProject.network.response.GenericResponse
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TimelineRepositoryImpl @Inject constructor(
    private val api: ServiceApi,
    private val dao: MarvelDao
) : TimelineRepository {
    override fun getComics(): Observable<GenericResponse<ComicResponse>> {
        return api.getComicTimeline()
    }

    override fun getComicsCharacter(
        id: Int,
        limit: Int,
        offset: Int?
    ): Observable<GenericResponse<ComicResponse>> {
        return api.getComicsCharacter(id, limit, offset)
    }

    override fun getStory(): Flow<List<FavoriteModel>> {
        return dao.getStory()
    }

    override fun getComicTimelineDao(): List<ComicResponse> =
        dao.getComicTimeline()

    override fun insertComicTimelineDao(comic: List<ComicResponse>) {
        return dao.insertComicTimeline(ComicTimelineMap(comic))
    }

    private fun ComicTimelineMap(comic: List<ComicResponse>): List<ComicTimelineResponse> {
        return comic.map { response ->
            ComicTimelineResponse(
                id = response.id,
                title = response.title,
                description = response.description,
                thumbnailModel = response.thumbnailModel,
                characters = response.characters,
                events = response.events
            )
        }
    }


}