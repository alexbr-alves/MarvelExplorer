package alexProject.data.model.comic

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ComicModelData(
    @SerializedName("total")
    val total: Int,
    @SerializedName("results")
    val result : List<ComicModel>
) : Serializable
