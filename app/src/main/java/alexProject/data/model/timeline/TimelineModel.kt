package alexProject.data.model.timeline

import alexProject.data.model.SizeItens
import alexProject.data.model.ThumbnailModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TimelineModel(
    @SerializedName("id")
    val id : Int,
    @SerializedName("title")
    val title : String,
    @SerializedName("name")
    val name : String,
    @SerializedName("description")
    val description : String,
    //@SerializedName("modified")
    //var modified : Date?,
    var type : TimelineType,
    @SerializedName("thumbnail")
    val thumbnailModel : ThumbnailModel,
    @SerializedName("comics")
    val comics : SizeItens,
    @SerializedName("events")
    val event : SizeItens,
    @SerializedName("characters")
    val character : SizeItens
) : Serializable

enum class TimelineType {
    COMIC,
    EVENT,
    CHARACTER
}