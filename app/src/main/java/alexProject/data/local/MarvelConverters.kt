package alexProject.data.local

import alexProject.data.model.ThumbnailModel
import androidx.room.TypeConverter
import com.google.gson.Gson

class MarvelConverters {

    @TypeConverter
    fun fromThumbnail(thumbnailModel : ThumbnailModel) : String = Gson().toJson(thumbnailModel)

    @TypeConverter
    fun toThumbnail(thumbnailModel : String) : ThumbnailModel =
        Gson().fromJson(thumbnailModel, ThumbnailModel::class.java)

}