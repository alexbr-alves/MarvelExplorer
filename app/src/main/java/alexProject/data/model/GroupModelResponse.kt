package alexProject.data.model

import alexProject.data.model.character.CharacterModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GroupModelResponse(
    @SerializedName("groups")
    val groups: List<GroupModel>
) : Serializable
