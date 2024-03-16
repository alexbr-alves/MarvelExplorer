package alexProject.data.model.games

import alexProject.data.model.ItemSiteMarvelModel
import com.google.gson.annotations.SerializedName

data class GamesResponseData(
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("all_games")
    val moviesMarvel: List<ItemSiteMarvelModel>,
)
