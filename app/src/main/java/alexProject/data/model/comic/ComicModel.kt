package alexProject.data.model.comic

import alexProject.data.model.SizeItens
import alexProject.data.model.ThumbnailModel
import alexProject.util.Constants
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ComicModel(
    @SerializedName("id")
    val id : Int,
    @SerializedName("title")
    val title : String,
    @SerializedName("description")
    val description : String,
    @SerializedName("thumbnail")
    val thumbnailModel : ThumbnailModel,
    @SerializedName("characters")
    val characters : SizeItens,
    @SerializedName("events")
    val events : SizeItens
) : Serializable
