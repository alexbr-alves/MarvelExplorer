package alexProject.network.response

import alexProject.data.model.SizeItens
import alexProject.data.model.ThumbnailModel
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CharacterResponse(
    @SerializedName("data")
    val apiResponse : CharacterModelData
)

data class CharacterModelData(
    @SerializedName("total")
    val total: Int,
    @SerializedName("results")
    val entityList : List<CharacterModel>
) : Serializable


@Entity(tableName = "characterModel")
data class CharacterModel(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name : String,
    @SerializedName("description")
    val description : String,
    @SerializedName("thumbnail")
    val thumbnailModel : ThumbnailModel,
    @SerializedName("comics")
    val comics : SizeItens,
    @SerializedName("events")
    val events : SizeItens
) : Serializable