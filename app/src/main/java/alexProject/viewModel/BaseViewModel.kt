package alexProject.viewModel

import alexProject.state.ResourceState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import retrofit2.Response

abstract class BaseViewModel : ViewModel() {
    private var job: Job? = null

    val onError = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    fun <T> handleResponse(
        response: Response<T>
    ): ResourceState<T> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }



}


