package alexProject.domain.repository

import alexProject.data.local.MarvelDao
import alexProject.data.model.FavoriteModel
import javax.inject.Inject

class DatabaseRepository @Inject constructor(
    private val dao: MarvelDao
) {
    suspend fun insert(characterModel: FavoriteModel) = dao.insert(characterModel)
    fun getAll() = dao.getAll()
    suspend fun delete(characterModel: FavoriteModel) = dao.delete(characterModel)
    suspend fun searchFavorite(id: Int) = dao.searchFavorite(id)
    suspend fun getStory() = dao.getStory()
}