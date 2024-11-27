package alexProject.viewModel

import alexProject.domain.repository.MarvelRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val repository: MarvelRepositoryImpl
) : BaseViewModel() {
}