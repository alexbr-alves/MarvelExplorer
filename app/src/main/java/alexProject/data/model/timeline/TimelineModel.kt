package alexProject.data.model.timeline

import alexProject.data.model.SizeItens
import alexProject.data.model.ThumbnailModel
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.Date

@Entity(tableName = "timelineModel")
data class TimelineModel(
    @PrimaryKey
    @SerializedName("id")
    val id : Int,

    @ColumnInfo(defaultValue = "Unknown")
    @SerializedName("title")
    val title : String,

    @NonNull
    @ColumnInfo(defaultValue = "Unknown")
    @SerializedName("name")
    val name : String,

    @ColumnInfo(defaultValue = "Unknown")
    @SerializedName("description")
    val description : String,

    @SerializedName("modified")
    var modified : Date?,
    var type : TimelineType,

    @SerializedName("thumbnail")
    val thumbnailModel : ThumbnailModel,

    @SerializedName("comics")
    val comics : SizeItens?,

    @SerializedName("events")
    val event : SizeItens?,

    @SerializedName("characters")
    val character : SizeItens?
) : Serializable

enum class TimelineType {
    COMIC,
    EVENT,
    CHARACTER
}