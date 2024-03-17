package alexProject.data.local

import alexProject.data.model.FavoriteModel
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
interface MarvelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characterModel : FavoriteModel) : Long

    @Query("SELECT * FROM favoriteModel ORDER BY id")
    fun getAll() : Flow<List<FavoriteModel>>

    @Delete
    suspend fun delete(characterModel : FavoriteModel)

    @Query("Select COUNT(*) from favoriteModel WHERE id = :characterId")
    suspend fun searchFavorite(characterId : Int) : Boolean

}