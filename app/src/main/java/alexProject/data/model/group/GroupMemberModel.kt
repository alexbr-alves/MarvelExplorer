package alexProject.data.model.group

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GroupMemberModel(
    @SerializedName("hero_name")
    val heroName: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("people_name")
    val peopleName: String
): Serializable
