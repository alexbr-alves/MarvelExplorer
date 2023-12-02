package alexProject.data.model.serie

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SeriesModelResponse(
    @SerializedName("data")
    val data : SeriesModelData
) : Serializable