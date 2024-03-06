package alexProject.data.local

import alexProject.data.model.FavoriteModel
import alexProject.data.model.timeline.TimelineModel
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FavoriteModel::class, TimelineModel::class], version = 1, exportSchema = false)
@TypeConverters(MarvelConverters::class)
abstract class MarvelDatabase : RoomDatabase() {
    abstract fun MarvelDao() : MarvelDao
}