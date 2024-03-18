package alexProject.data.model.event

import alexProject.data.model.SizeItens
import alexProject.data.model.ThumbnailModel
import alexProject.util.Constants
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EventModel(
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
    @SerializedName("comics")
    val comics : SizeItens,
    val type : String = Constants.EVENT
) : Serializable
