package com.example.loadmoredemo

import androidx.lifecycle.LiveData
import com.example.loadmoredemo.model.RespositoryResponse
import javax.inject.Inject

class SearchRepositoriesRepos @Inject constructor(
    val searchRepositoriesApi: SearchRepositoriesApi
) {
    suspend fun getRepositories(sortType: SearchRepositoriesApi.SortType, query: String,
                                perPage: Int, page: Int ): LiveData<MyResponse<RespositoryResponse>> {
        return MyResponse.createMyReponse {
            searchRepositoriesApi?.getRepositories(sortType, query, perPage, page)?.execute()
        }
    }
}
