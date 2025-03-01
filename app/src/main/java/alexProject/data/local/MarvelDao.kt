package alexProject.data.local

import alexProject.data.model.FavoriteModel
import alexProject.network.response.CharacterResponse
import alexProject.network.response.GenericResponse
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow
@Dao
interface MarvelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characterModel : FavoriteModel) : Long

    @Query("SELECT * FROM favoriteModel ORDER BY id")
    fun getAll() : Flow<List<FavoriteModel>>

    @Query("SELECT * FROM favoriteModel WHERE type = 'Character' ORDER BY insertionTime DESC")
    fun getStory() : Flow<List<FavoriteModel>>

    @Delete
    suspend fun delete(characterModel : FavoriteModel)

    @Query("Select COUNT(*) from favoriteModel WHERE id = :characterId")
    suspend fun searchFavorite(characterId : Int) : Boolean

    @Query("SELECT * FROM characterModel ORDER BY name")
    fun getCharacters(): List<CharacterResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: List<CharacterResponse>)
}