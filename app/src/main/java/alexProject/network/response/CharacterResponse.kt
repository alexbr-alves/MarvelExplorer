package alexProject.network.response

import alexProject.data.model.SizeItens
import alexProject.data.model.ThumbnailModel
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "characterModel")
data class CharacterResponse(
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