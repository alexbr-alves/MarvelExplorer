package alexProject.data.repository

import alexProject.data.model.FavoriteModel
import alexProject.network.response.ComicResponse
import alexProject.network.response.GenericResponse
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

interface TimelineRepository {
    fun getComics() : Observable<GenericResponse<ComicResponse>>
    fun getComicsCharacter(id: Int, limit: Int, offset: Int?) : Observable<GenericResponse<ComicResponse>>
    fun getStory() : Flow<List<FavoriteModel>>
}