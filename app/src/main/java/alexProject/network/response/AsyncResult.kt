package alexProject.network.response

sealed class AsyncResult<out T: Any?> {
    data class Success<out T: Any?>(val value: T? = null): AsyncResult<T>()
    data class Failure(val error: ErrorResponse): AsyncResult<Nothing>()
    object Loading: AsyncResult<Nothing>()
}