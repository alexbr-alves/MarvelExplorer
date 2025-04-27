package alexProject.data.local

import alexProject.data.model.SizeItens
import alexProject.data.model.ThumbnailModel
import alexProject.network.response.ComicResponse
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date


class MarvelConverters {

    @TypeConverter
    fun fromThumbnail(thumbnailModel : ThumbnailModel) : String = Gson().toJson(thumbnailModel)

    @TypeConverter
    fun toThumbnail(thumbnailModel : String) : ThumbnailModel =
        Gson().fromJson(thumbnailModel, ThumbnailModel::class.java)

    @TypeConverter
    fun fromDate(date: Date?): String? {
        return date?.time?.toString()
    }

    @TypeConverter
    fun toDate(value: String?): Date? {
        return value?.toLong()?.let { Date(it) }
    }

    @TypeConverter
    fun fromSizeItens(sizeItens: SizeItens?): String? {
        return Gson().toJson(sizeItens)
    }

    @TypeConverter
    fun toSizeItens(sizeItensString: String?): SizeItens? {
        return Gson().fromJson(sizeItensString, SizeItens::class.java)
    }

    @TypeConverter
    fun fromString(value: String?): List<ComicResponse>? {
        val listType = object : TypeToken<List<ComicResponse>>() {}.type
        return value?.let { Gson().fromJson(it, listType) }
    }

    @TypeConverter
    fun fromList(list: List<ComicResponse>?): String? {
        return list?.let { Gson().toJson(it) }
    }
}