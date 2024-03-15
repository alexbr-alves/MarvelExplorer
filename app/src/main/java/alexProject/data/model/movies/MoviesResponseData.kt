package alexProject.data.model.movies

import alexProject.data.model.ItemSiteMarvelModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MoviesResponseData(
    @SerializedName("image")
    val image: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("marvel_movies")
    val moviesMarvel: List<ItemSiteMarvelModel>,
    @SerializedName("other_movies")
    val otherMovies: List<ItemSiteMarvelModel>
) : Serializable
