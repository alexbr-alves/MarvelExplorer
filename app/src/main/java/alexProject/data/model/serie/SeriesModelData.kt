package alexProject.data.model.serie

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SeriesModelData(
    @SerializedName("results")
    val result : List<SeriesModel>
) : Serializable