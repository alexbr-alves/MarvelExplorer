package alexProject.data.repository

import alexProject.data.model.FavoriteModel

interface DetailsRepository {
    suspend fun insert(favorite: FavoriteModel): Long
    suspend fun searchFavorite(characterId: Int): Boolean
}