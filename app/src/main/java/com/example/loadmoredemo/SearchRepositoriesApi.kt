package com.example.loadmoredemo
import com.example.loadmoredemo.model.Repository
import com.example.loadmoredemo.model.RespositoryResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
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