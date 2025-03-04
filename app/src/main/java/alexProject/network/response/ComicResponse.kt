package alexProject.network.response

import alexProject.data.model.SizeItens
import alexProject.data.model.ThumbnailModel
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import javax.annotation.Nonnull
import javax.annotation.Nullable

@Entity(tableName = "comicModel")
open class ComicResponse(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id : Int,
    @SerializedName("title")
    val title : String,
    @SerializedName("description")
    val description : String? = "",
    @SerializedName("thumbnail")
    val thumbnailModel : ThumbnailModel,
    @SerializedName("characters")
    val characters : SizeItens,
    @SerializedName("events")
    val events : SizeItens
) : Serializable

@Entity(tableName = "comicTimeline")
class ComicTimelineResponse(
    id: Int,
    title: String,
    description: String?,
    thumbnailModel: ThumbnailModel,
    characters: SizeItens,
    events: SizeItens
) : ComicResponse(id, title, description, thumbnailModel, characters, events)
