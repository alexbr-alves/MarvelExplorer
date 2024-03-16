package alexProject.viewModel

import alexProject.state.ResourceState
import alexProject.state.SingleLiveEvent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import retrofit2.Response

abstract class BaseViewModel : ViewModel() {
    private var job: Job? = null

    val onError = MutableLiveData<String?>()
    val isLoading = SingleLiveEvent<Boolean>()

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
        when(val response = handleResponse(repository)) {
            is ResourceState.Success -> {
                isLoading.value = false
                item.value = response
            }
            is ResourceState.Error -> {
                onError.value = response.message
            }
            is ResourceState.Empty -> {
                onError.value = response.message
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}


