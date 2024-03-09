package alexProject.data.model.group

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GroupModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("current_members")
    val currentMembers: List<GroupMemberModel>,
    @SerializedName("former_members")
    val formerMembers: List<GroupMemberModel>
) : Serializable