package alexProject.data.model.movies

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MoviesResponseData(
    @SerializedName("image")
    val image: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("marvel_movies")
    val moviesMarvel: List<MoviesModel>,
    @SerializedName("other_movies")
    val otherMovies: List<MoviesModel>
) : Serializable
