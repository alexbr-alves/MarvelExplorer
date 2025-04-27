package alexProject.viewModel.movies

import alexProject.data.model.movies.MoviesModelResponse
import alexProject.domain.useCase.GetMoviesUseCase
import alexProject.state.SingleLiveEvent
import alexProject.viewModel.MarvelViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : MarvelViewModel() {

    val listItems = SingleLiveEvent<MoviesModelResponse>()

    fun getMovies() {
        handleObservable(
            observable = getMoviesUseCase(),
            successHandler = {
                listItems.value = it
            }
        )
    }

}