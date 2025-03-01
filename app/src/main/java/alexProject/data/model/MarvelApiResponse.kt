import com.google.gson.annotations.SerializedName

data class MarvelApiResponse<T>(
    @SerializedName("data")
    val data: DataContainer<T>
)

data class DataContainer<T>(
    @SerializedName("total")
    val total: Int,
    @SerializedName("results")
    val results: List<T>
)