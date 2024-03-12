package alexProject.data.model.movies

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MoviesModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("url")
    val url: String
) : Serializable
