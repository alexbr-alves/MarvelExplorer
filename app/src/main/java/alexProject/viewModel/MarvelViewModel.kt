package alexProject.viewModel

import alexProject.network.response.AsyncResult
import alexProject.network.response.ErrorResponse
import alexProject.state.SingleLiveEvent
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class MarvelViewModel : ViewModel() {
    protected open val disposable by lazy {
        CompositeDisposable()
    }

    val onError = SingleLiveEvent<ErrorResponse>()
    val onLoading = SingleLiveEvent<Boolean>()

    @JvmName("subscribleNullable")
    fun <T : Any> handleObservable(
        source: Observable<AsyncResult<T?>>?,
        onSuccess: (result: T?) -> Unit,
        onFailure: ((error: ErrorResponse) -> Unit)? = null
    ) {
        source?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.subscribe { result ->
                onLoading.value = result is AsyncResult.Loading
                if (result is AsyncResult.Success) {
                    onSuccess(result.value)
                } else if (result is AsyncResult.Failure) {
                    onFailure?.invoke(result.error) ?: run { onError.value = result.error }
                }
            }
            ?.let { disposable::add }
    }

    @JvmName("subscribleNonNullable")
    fun <T : Any> handleObservable(
        observable: Observable<AsyncResult<T>>?,
        successHandler: (result: T) -> Unit,
        errorHandler: ((error: ErrorResponse) -> Unit)? = null
    ) {
        observable?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.subscribe { result ->
                onLoading.value = result is AsyncResult.Loading
                if (result is AsyncResult.Success) {
                    successHandler(result.value!!)
                } else if (result is AsyncResult.Failure) {
                    errorHandler?.invoke(result.error) ?: run { onError.value = result.error }
                }
            }
            ?.let { disposable::add }
    }

    fun <T : Any> handleObservable(
        observable: Observable<AsyncResult<T?>>?,
        resultHandler: (result: AsyncResult<T?>) -> Unit
    ) {
        observable?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.subscribe { result ->
                resultHandler(result)
            }
            ?.let { disposable::add }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}