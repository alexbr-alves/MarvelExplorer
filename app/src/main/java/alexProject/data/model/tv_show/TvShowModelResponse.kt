package alexProject.data.model.tv_show

import com.google.gson.annotations.SerializedName

data class TvShowModelResponse(
    @SerializedName("tv_shows")
    val tvShows: TvShowResponseData
)
