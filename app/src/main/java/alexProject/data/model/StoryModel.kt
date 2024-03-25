package alexProject.data.model

import alexProject.data.model.comic.ComicModel

data class StoryModel(
    val id: Int,
    val thumbnailModel: ThumbnailModel,
    val comics: List<ComicModel>,
)
