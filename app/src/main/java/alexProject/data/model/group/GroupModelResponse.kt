package alexProject.data.model.group

import alexProject.data.model.group.GroupModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GroupModelResponse(
    @SerializedName("groups")
    val groups: List<GroupModel>
) : Serializable
