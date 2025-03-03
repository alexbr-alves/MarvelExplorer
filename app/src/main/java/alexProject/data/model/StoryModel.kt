package alexProject.data.model

import alexProject.network.response.ComicResponse

data class StoryModel(
    val id: Int,
    val thumbnailModel: ThumbnailModel,
    val name: String,
    val comics: List<ComicResponse>,
)
