package alexProject.data.model.timeline

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TimelineModelData(
    @SerializedName("results")
    val result : List<TimelineModel>
) : Serializable