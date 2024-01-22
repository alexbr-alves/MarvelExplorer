package alexProject.data.model.timeline

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TimelineModelResponse(
    @SerializedName("data")
    val data : TimelineModelData
) : Serializable