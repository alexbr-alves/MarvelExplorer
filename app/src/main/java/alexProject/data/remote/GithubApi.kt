package alexProject.data.remote

import alexProject.data.model.GroupModelResponse
import retrofit2.Response
import retrofit2.http.GET


const val GITHUB_BASE_URL = "https://raw.githubusercontent.com/alexbr-alves/database/master/"
const val MARVEL_GROUPS = "marvel_groups.json"
interface GithubApi {
    @GET(GITHUB_BASE_URL + MARVEL_GROUPS)
    suspend fun getGroups(): Response<GroupModelResponse>

}
