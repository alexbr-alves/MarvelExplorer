package alexProject.data.model.tv_show

import alexProject.data.model.ItemSiteMarvelModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TvShowResponseData(
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("marvel_on_disney_plus")
    val marvelOnDisneyPlus: List<ItemSiteMarvelModel>,
    @SerializedName("non_fiction_disney_plus")
    val nonFictionDisneyPlus: List<ItemSiteMarvelModel>,
    @SerializedName("marvel_series_d_plus")
    val marvelSeriesDPlus: List<ItemSiteMarvelModel>,
    @SerializedName("marvel_on_hulu")
    val marvelOnHulu: List<ItemSiteMarvelModel>,
    @SerializedName("animation")
    val animation: List<ItemSiteMarvelModel>
) : Serializable
