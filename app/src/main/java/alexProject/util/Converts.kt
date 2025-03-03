package alexProject.util

import alexProject.data.model.FavoriteModel
import alexProject.data.model.ThumbnailModel
import alexProject.data.model.SizeItens
import alexProject.network.response.ComicResponse
import alexProject.network.response.CharacterResponse
import alexProject.data.model.event.EventModel

class Converts {

    fun characterToFavorite(characterResponse : CharacterResponse) : FavoriteModel {
        return FavoriteModel(
            characterResponse.id,
            characterResponse.name,
            characterResponse.description ?: "",
            Constants.CHARACTER,
            thumbnailModel = ThumbnailModel(
                path = characterResponse.thumbnailModel.path,
                extension = characterResponse.thumbnailModel.extension
            ),
            item1 = characterResponse.comics.available ?: 0,

            item2 = characterResponse.events.available ?: 0
        )
    }

    fun favoriteToCharacter(favoriteModel : FavoriteModel) : CharacterResponse {
        return CharacterResponse(
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

    fun favoriteToComic(favoriteModel : FavoriteModel) : ComicResponse {
        return ComicResponse(
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

    fun comicToFavorite(comicResponse : ComicResponse) : FavoriteModel {
        return FavoriteModel(
            comicResponse.id,
            comicResponse.title,
            comicResponse.description ?: "",
            Constants.COMIC,
            ThumbnailModel(
                comicResponse.thumbnailModel.path,
                comicResponse.thumbnailModel.extension
            ),
            comicResponse.characters.available ?: 0,
            comicResponse.events.available ?: 0
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