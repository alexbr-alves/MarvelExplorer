package alexProject.domain.repository

import alexProject.data.local.MarvelDao
import alexProject.data.remote.ServiceApi
import alexProject.data.repository.ComicRepository
import alexProject.network.response.ComicResponse
import javax.inject.Inject

class ComicRepositoryImpl @Inject constructor(
    private val api: ServiceApi,
    private val dao: MarvelDao
) : ComicRepository {
    override fun getComics(
        titleStartsWith: String?,
        limit: Int,
        offset: Int?,
        orderBy: String?
    ) = api.comics(titleStartsWith, limit, offset, orderBy)

    override fun getComicEvent(eventId: Int, limit: Int, offset: Int?) =
        api.getComicEvent(eventId, limit, offset)

    override fun getComicsCharacter(characterId: Int, limit: Int?, offset: Int?) =
        api.getComicsCharacter(characterId, limit, offset)

    override fun getComicDao(): List<ComicResponse> =
        dao.getComic()

    override fun insertComicDao(comic: List<ComicResponse>) =
        dao.insertComic(comic)
}