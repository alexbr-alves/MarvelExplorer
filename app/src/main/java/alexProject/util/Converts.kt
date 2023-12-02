package alexProject.util

import alexProject.data.model.FavoriteModel
import alexProject.data.model.ThumbnailModel
import alexProject.data.model.character.CharacterModel
import alexProject.data.model.SizeItens
import alexProject.data.model.comic.ComicModel
import alexProject.data.model.event.EventModel
import alexProject.data.model.timeline.TimelineModel

class Converts {

    fun characterToFavorite(characterModel : CharacterModel) : FavoriteModel {
        return FavoriteModel(
            characterModel.id,
            characterModel.name,
            characterModel.description ?: "",
            Constants.CHARACTER,
            thumbnailModel = ThumbnailModel(
                path = characterModel.thumbnailModel.path,
                extension = characterModel.thumbnailModel.extension
            )
        )
    }

    fun favoriteToCharacter(favoriteModel : FavoriteModel) : CharacterModel {
        return CharacterModel(
            favoriteModel.id,
            favoriteModel.title,
            favoriteModel.description ?: "",
            ThumbnailModel(
                favoriteModel.thumbnailModel.path,
                favoriteModel.thumbnailModel.extension
            ),
            SizeItens(
                available = 0
            ),
            SizeItens(
                available = 0
            )
        )
    }

    fun favoriteToComic(favoriteModel : FavoriteModel) : ComicModel {
        return ComicModel(
            favoriteModel.id,
            favoriteModel.title,
            favoriteModel.description,
            ThumbnailModel(
                favoriteModel.thumbnailModel.path,
                favoriteModel.thumbnailModel.extension
            ),
            SizeItens(
                available = 0
            ),
            SizeItens(
                available = 0
            )
        )
    }

    fun favoriteToEvent(favoriteModel : FavoriteModel) : EventModel {
        return EventModel(
            favoriteModel.id,
            favoriteModel.title,
            favoriteModel.description ?: "",
            ThumbnailModel(
                favoriteModel.thumbnailModel.path,
                favoriteModel.thumbnailModel.extension
            ),
            SizeItens(
                available = 0
            ),
            SizeItens(
                available = 0
            )
        )
    }

    fun comicToFavorite(comicModel : ComicModel) : FavoriteModel {
        return FavoriteModel(
            comicModel.id,
            comicModel.title,
            comicModel.description ?: "",
            Constants.COMIC,
            ThumbnailModel(
                comicModel.thumbnailModel.path,
                comicModel.thumbnailModel.extension
            )
        )
    }

    fun eventToFavorite(eventModel : EventModel) : FavoriteModel {
        return FavoriteModel(
            eventModel.id,
            eventModel.title,
            eventModel.description ?: "",
            Constants.EVENT,
            ThumbnailModel(
                eventModel.thumbnailModel.path,
                eventModel.thumbnailModel.extension
            )
        )
    }

    fun timelineToCharacter(timelineModel : TimelineModel) : CharacterModel {
        return CharacterModel(
            timelineModel.id,
            timelineModel.title,
            timelineModel.description,
            ThumbnailModel(
                timelineModel.thumbnailModel.path,
                timelineModel.thumbnailModel.extension
            ),
            SizeItens(
                available = 0
            ),
            SizeItens(
                available = 0
            )
        )
    }

    fun timelineToComic(timelineModel : TimelineModel) : ComicModel {
        return ComicModel(
            timelineModel.id,
            timelineModel.title,
            timelineModel.description ?: "",
            ThumbnailModel(
                timelineModel.thumbnailModel.path,
                timelineModel.thumbnailModel.extension
            ),
            SizeItens(
                available = 0
            ),
            SizeItens(
                available = 0
            )
        )
    }

    fun timelineToEvent(timelineModel : TimelineModel) : EventModel {
        return EventModel(
            timelineModel.id,
            timelineModel.title,
            timelineModel.description ?: "",
            ThumbnailModel(
                timelineModel.thumbnailModel.path,
                timelineModel.thumbnailModel.extension
            ),
            SizeItens(
                available = 0
            ),
            SizeItens(
                available = 0
            )
        )
    }

}