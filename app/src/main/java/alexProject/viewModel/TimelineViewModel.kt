package alexProject.viewModel

import alexProject.R
import alexProject.data.model.timeline.TimelineModel
import alexProject.data.model.timeline.TimelineModelResponse
import alexProject.data.model.timeline.TimelineType
import alexProject.repository.MarvelRepository
import alexProject.state.ResourceState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class TimelineViewModel @Inject constructor(
    private val repository : MarvelRepository
) : ViewModel() {

    private val _listItems =
        MutableStateFlow<ResourceState<Collection<TimelineModel>>>(ResourceState.Loading())
    val listItems : StateFlow<ResourceState<Collection<TimelineModel>>> = _listItems

    init {
        fetch()
    }

    fun fetch() = viewModelScope.launch {
        safeFetch()
    }

    private suspend fun safeFetch() {
        try {
            val comicList = handleResponse(repository.getComicTimeline())
            val eventList = handleResponse(repository.getEventTimeline())
            val characterList = handleResponse(repository.getCharacterTimeline())
            val combinedList = mutableListOf<TimelineModel>()

            comicList.data?.let {
                it.data.result.map { it.type = TimelineType.COMIC }
            }
            comicList.data?.let { data ->
                combinedList.addAll(
                    (data.data.result ?: emptyList()) as Collection<TimelineModel>
                )

                eventList.data?.let {
                    it.data.result.map { it.type = TimelineType.EVENT }
                }

                eventList.data?.let { data ->
                    combinedList.addAll(
                        (data.data.result ?: emptyList()) as Collection<TimelineModel>
                    )
                }

                characterList.data?.let {
                    it.data.result.map { it.type = TimelineType.CHARACTER }
                }


                characterList.data?.let { data ->
                    combinedList.addAll(
                        (data.data.result ?: emptyList()) as Collection<TimelineModel>
                    )
                }
            }

            //combinedList.sortByDescending { it.modified }

            _listItems.value = ResourceState.Success(combinedList)
        } catch (t : Throwable) {
            when (t) {
                is IOException -> _listItems.value =
                    ResourceState.Error(R.string.error_internet_connection.toString())

                else -> _listItems.value =
                    ResourceState.Error(R.string.error_data_conversion_failure.toString())
            }
        }
    }

    private fun handleResponse(response : Response<TimelineModelResponse>) : ResourceState<TimelineModelResponse> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }

}
