package alexProject.data.model

import alexProject.network.response.ComicModel

data class StoryModel(
    val id: Int,
    val thumbnailModel: ThumbnailModel,
    val name: String,
    val comics: List<ComicModel>,
)
