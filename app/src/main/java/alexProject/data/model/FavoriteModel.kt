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
    val thumbnailModel : ThumbnailModel,
    @SerializedName("item1")
    val item1 : Int,
    @SerializedName("item2")
    val item2 : Int,
    @SerializedName("insertionTime")
    val insertionTime: Long? = System.currentTimeMillis()
) : Serializable