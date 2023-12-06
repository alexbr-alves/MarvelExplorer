package alexProject.data.model

import com.google.gson.annotations.SerializedName

data class GenericModel(
    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name : String,
    @SerializedName("title")
    val title : String,
    @SerializedName("thumbnail")
    val thumbnailModel : ThumbnailModel,
)
