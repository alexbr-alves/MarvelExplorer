package alexProject.data.model.movies

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MoviesModelResponse (
    @SerializedName("movies")
    val movies: MoviesResponseData
) : Serializable