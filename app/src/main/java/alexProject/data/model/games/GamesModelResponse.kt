package alexProject.data.model.games

import com.google.gson.annotations.SerializedName

data class GamesModelResponse(
    @SerializedName("games")
    val movies: GamesResponseData
)
