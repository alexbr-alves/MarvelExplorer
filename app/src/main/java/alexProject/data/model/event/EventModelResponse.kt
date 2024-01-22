package alexProject.data.model.event

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class EventModelResponse(
    @SerializedName("data")
    val data : EventModelData
) : Serializable
