package alexProject.domain.repository

import alexProject.data.local.MarvelDao
import alexProject.data.model.FavoriteModel
import alexProject.data.repository.DetailsRepository
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val dao: MarvelDao
) : DetailsRepository {

    override suspend fun insert(favorite: FavoriteModel): Long {
        return dao.insert(favorite)
    }

    override suspend fun searchFavorite(characterId: Int): Boolean {
        return dao.searchFavorite(characterId)
    }
}