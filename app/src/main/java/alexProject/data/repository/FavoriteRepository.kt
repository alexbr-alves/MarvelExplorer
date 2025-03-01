package alexProject.data.repository

import alexProject.data.model.FavoriteModel
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getAll() : Flow<List<FavoriteModel>>
    suspend fun delete(favorite : FavoriteModel) : Unit
}