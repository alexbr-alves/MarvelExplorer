package alexProject.util

import alexProject.data.model.SizeItens
import alexProject.data.model.ThumbnailModel
import alexProject.data.model.character.CharacterModel
import alexProject.data.model.comic.ComicModel
import alexProject.data.model.event.EventModel

object Constants {
    const val DATABASE_NAME = "marvel.db"

    const val APIKEY = "apikey"
    const val HASH = "hash"
    const val TS = "ts"

    const val GENERIC_TITLE_ERROR = "Algo de errado aconteceu!"
    const val GENERIC_MESSAGE_ERROR = "Tente novamente mais tarde!"

    const val IMAGE_NOT_AVAILABLE =
        "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available"

    const val CHARACTER = "Character"
    const val COMIC = "Comic"
    const val EVENT = "Event"
    const val HOME = "home"

    const val VERIFIED = "verified"
    const val NOT_VERIFIED = "not_verified"

    val COMIC_INVALID = ComicModel(
        id = 0,
        title = "",
        description = "",
        thumbnailModel = ThumbnailModel(
            path = "",
            extension = ""
        ),
        characters = SizeItens(
            available = 0,
        ),
        events = SizeItens(
            available = 0,
        )
    )

    val CHARACTER_INVALID = CharacterModel(
        id = 0,
        name = "",
        description = "",
        thumbnailModel = ThumbnailModel(
            path = "",
            extension = ""
        ),
        comics = SizeItens(
            available = 0,
        ),
        events = SizeItens(
            available = 0,
        )
    )

    val EVENT_INVALID = EventModel(
        id = 0,
        title = "",
        description = "",
        thumbnailModel = ThumbnailModel(
            path = "",
            extension = ""
        ),
        characters = SizeItens(
            available = 0,
        ),
        comics = SizeItens(
            available = 0,
        )
    )


}