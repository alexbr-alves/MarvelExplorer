package alexProject.domain.repository

import alexProject.data.local.MarvelDao
import alexProject.data.model.FavoriteModel
import alexProject.data.repository.FavoriteRepository
import alexProject.data.repository.MarvelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavotiteRepositoryImpl @Inject constructor(
    private val dao: MarvelDao
) : FavoriteRepository {
    override fun getAll(): Flow<List<FavoriteModel>> {
        return dao.getAll()
    }

    override suspend fun delete(favorite: FavoriteModel) {
        return dao.delete(favorite)
    }

}