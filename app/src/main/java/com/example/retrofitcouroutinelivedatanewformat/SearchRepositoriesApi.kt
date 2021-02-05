package com.example.retrofitcouroutinelivedatanewformat
import com.example.retrofitcouroutinelivedatanewformat.model.RespositoryResponse
import retrofit2.http.*

interface SearchRepositoriesApi {

    enum class SortType(val value: String) {
        STAR("stars"),
        FORKS("forks"),
        HELP_WANTED_ISSUES("help-wanted-issues"),
        UPDATED("updated")
    }

    companion object{
        const val GET_REPOSITORIES = "search/repositories"
    }
    @GET(GET_REPOSITORIES)
    suspend fun getRepositories(@Query("sort") sortType: SortType, @Query("q") query: String,
                        @Query("per_page") perPage: Int,  @Query("page") page: Int ): RespositoryResponse
}