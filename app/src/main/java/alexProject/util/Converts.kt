package alexProject.util

import alexProject.data.model.FavoriteModel
import alexProject.data.model.ThumbnailModel
import alexProject.data.model.SizeItens
import alexProject.network.response.CharacterModel
import alexProject.data.model.comic.ComicModel
import alexProject.data.model.event.EventModel

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
            ),
            item1 = characterModel.comics.available ?: 0,

            item2 = characterModel.events.available ?: 0
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
                available = favoriteModel.item1
            ),
            SizeItens(
                available = favoriteModel.item2
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
                available = favoriteModel.item1
            ),
            SizeItens(
                available = favoriteModel.item2
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
                available = favoriteModel.item1
            ),
            SizeItens(
                available = favoriteModel.item2
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
            ),
            comicModel.characters.available ?: 0,
            comicModel.events.available ?: 0
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
            ),
            eventModel.characters.available ?: 0,
            eventModel.comics.available ?: 0
        )
    }

}