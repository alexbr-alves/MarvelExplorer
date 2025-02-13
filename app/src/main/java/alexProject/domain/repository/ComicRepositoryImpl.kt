package alexProject.domain.repository

import alexProject.data.remote.ServiceApi
import alexProject.data.repository.ComicRepository
import javax.inject.Inject

class ComicRepositoryImpl @Inject constructor(
    private val api: ServiceApi
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

}