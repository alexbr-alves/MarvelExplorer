package alexProject.data.local

import alexProject.data.model.FavoriteModel
import alexProject.network.response.CharacterResponse
import alexProject.network.response.ComicResponse
import alexProject.network.response.ComicTimelineResponse
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FavoriteModel::class, CharacterResponse::class, ComicResponse::class, ComicTimelineResponse::class], version = 1, exportSchema = false)
@TypeConverters(MarvelConverters::class)
abstract class MarvelDatabase : RoomDatabase() {
    abstract fun MarvelDao() : MarvelDao
}