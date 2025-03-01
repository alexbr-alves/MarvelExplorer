package alexProject.util

import alexProject.network.response.AsyncResult
import alexProject.network.response.ErrorResponse
import alexProject.network.response.GenericResponse
import alexProject.util.Constants.GENERIC_MESSAGE_ERROR
import alexProject.util.Constants.GENERIC_TITLE_ERROR
import android.util.Log
import com.google.gson.Gson
import io.reactivex.Observable
import retrofit2.HttpException
import retrofit2.Response

fun <T : Any?> Observable<T>.toAsyncResult(): Observable<AsyncResult<T>> {
    return flatMap {
        checkIsSuccessFullRequest(it)
        Observable.just<AsyncResult<T>>(AsyncResult.Success(it))
    }
        .doOnError { Log.e("toAsyncResult", it.toString()) }
        .onErrorReturn { return@onErrorReturn getErrorResponse(it) }
        .startWith(AsyncResult.Loading)
}

private fun checkIsSuccessFullRequest(generic: Any?) {
    if (generic is Response<*> && generic.code() !in 200..299) {
        throw HttpException(generic)
    }
}

private fun getErrorResponse(throwable: Throwable): AsyncResult.Failure {
    if (throwable is HttpException) {
        val errorString = throwable.response()?.errorBody()?.string()
        val statusCode = throwable.code()

        val genericResponse = runCatching {
            Gson().fromJson(errorString, GenericResponse::class.java)
        }.getOrNull()

        val failureResult = AsyncResult.Failure(
            ErrorResponse(
                title = genericResponse?.error?.title,
                code = statusCode
            )
        )
        failureResult.error.message = if (genericResponse?.apiResponse != null) {
            genericResponse.error?.message
        } else {
            GENERIC_MESSAGE_ERROR
        }

        failureResult.error.title = if (genericResponse?.apiResponse != null) {
            genericResponse.error?.title
        } else {
            GENERIC_TITLE_ERROR
        }
        failureResult.error.type = genericResponse?.type
        return failureResult
    }
    return AsyncResult.Failure(ErrorResponse())
}
