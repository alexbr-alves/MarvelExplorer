package alexProject.data.model.event

import alexProject.data.model.ThumbnailModel
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
    val thumbnailModel : ThumbnailModel
) : Serializable
