package alexProject.network.response

import alexProject.util.Constants.GENERIC_MESSAGE_ERROR
import alexProject.util.Constants.GENERIC_TITLE_ERROR
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GenericResponse<T>(
    @SerializedName("conteudo")
    val result: T? = null,
    @SerializedName("erro")
    val error: ErrorResponse? = null,
    @SerializedName("type")
    val type: String? = null
)

class ErrorResponse(
    @SerializedName("titulo")
    var title: String? = GENERIC_TITLE_ERROR,
    @SerializedName("mensagem")
    var message: String? = GENERIC_MESSAGE_ERROR,
    @Expose(serialize = false)
    val code: Int? = null,
    var type: String? = null
)
