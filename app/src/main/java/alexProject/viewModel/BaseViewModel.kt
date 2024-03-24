package alexProject.viewModel

import alexProject.state.ResourceState
import alexProject.state.SingleLiveEvent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.SocketException

abstract class BaseViewModel : ViewModel() {
    private var job: Job? = null

    val onError = MutableLiveData<String?>()
    val isLoading = SingleLiveEvent<Boolean>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is SocketException -> onError.value = "Erro de conexão: ${throwable.message}"
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

    fun <T> handleFetchResponse(repository: Response<T>, item: MutableLiveData<ResourceState<T>>) {
        isLoading.value = true
        job = viewModelScope.launch(exceptionHandler) {
            try {
                val response = handleResponse(repository)
                when (response) {
                    is ResourceState.Success -> item.value = response
                    is ResourceState.Error -> onError.value = response.message
                    is ResourceState.Empty -> onError.value = response.message
                }
            } catch (e: Exception) {
                // Lidar com a exceção de forma adequada, por exemplo, definindo o estado de erro
                onError.value = "Erro ao processar resposta da solicitação: ${e.message}"
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


