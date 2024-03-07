package alexProject.data.model

import alexProject.data.model.character.CharacterModel
import com.google.gson.annotations.SerializedName

data class GroupModelResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("current_members")
    val currentMembers: List<CharacterModel>,
    @SerializedName("former_members")
    val formerMembers: List<CharacterModel>
)
