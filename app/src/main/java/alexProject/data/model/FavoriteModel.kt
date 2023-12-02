package alexProject.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "favoriteModel")
data class FavoriteModel(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id : Int,
    @SerializedName("title")
    val title : String,
    @SerializedName("description")
    val description : String,
    @SerializedName("type")
    val type : String,
    @SerializedName("thumbnail")
    val thumbnailModel : ThumbnailModel
) : Serializable