package alexProject.domain.repository

import alexProject.data.local.MarvelDao
import alexProject.data.model.FavoriteModel
import alexProject.data.remote.ServiceApi
import alexProject.data.repository.TimelineRepository
import alexProject.network.response.ComicModel
import alexProject.network.response.GenericResponse
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

class TimelineRepositoryImpl @Inject constructor(
    private val api: ServiceApi,
    private val dao: MarvelDao
) : TimelineRepository {
    override fun getComics(): Observable<GenericResponse<ComicModel>> {
        return api.getComicTimeline()
    }

    override fun getComicsCharacter(
        id: Int,
        limit: Int,
        offset: Int?
    ): Observable<GenericResponse<ComicModel>> {
        return api.getComicsCharacter(id, limit, offset)
    }

    override suspend fun getStory(): List<FavoriteModel> {
        return dao.getStory().first()
    }
}