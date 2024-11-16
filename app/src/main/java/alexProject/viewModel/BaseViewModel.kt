package alexProject.viewModel

import alexProject.state.ResourceState
import alexProject.state.SingleLiveEvent
import android.net.http.HttpException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException

abstract class BaseViewModel : ViewModel() {
    private var job: Job? = null

    val onError = MutableLiveData<String?>()
    val isLoading = SingleLiveEvent<Boolean>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is SocketException -> onError.value = "Erro de conexão: ${throwable.message}"
            is SocketTimeoutException -> onError.value =
                "A requisição demorou demais. Tente novamente."

            else -> onError.value = "Erro desconhecido: ${throwable.message}"
        }
        isLoading.value = false
    }

    private fun <T> handleResponse(
        response: Response<T>
    ): ResourceState<T> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }


    fun <T> handleFetchResponse(
        repository: Response<T>,
        item: MutableLiveData<ResourceState<T>>
    ) {
        isLoading.value = true
        job = viewModelScope.launch(exceptionHandler) {
            try {
                val response = handleResponse(repository)
                when (response) {
                    is ResourceState.Success -> item.value = response
                    is ResourceState.Error -> onError.value = response.message
                    is ResourceState.Empty -> onError.value = response.message
                }
            } catch (e: SocketTimeoutException) {
                handleFetchResponse(repository, item)
                onError.value = "A requisição demorou demais. Tente novamente."
            } catch (e: SocketException) {
                onError.value = "Erro de conexão: ${e.message}"
            } catch (e: HttpException) {
                onError.value = "Erro HTTP: ${e.message}"
            } catch (e: IOException) {
                onError.value = "Erro de rede: ${e.message}"
            } catch (e: Exception) {
                Timber.e("Erro ao processar resposta: ${e.message}")
                onError.value = "Erro desconhecido: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}


