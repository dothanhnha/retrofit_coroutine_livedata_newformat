package com.example.retrofitcouroutinelivedatanewformat

import androidx.lifecycle.LiveData
import com.example.retrofitcouroutinelivedatanewformat.model.RespositoryResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SearchRepositoriesRepos @Inject constructor(
    val searchRepositoriesApi: SearchRepositoriesApi
) {

    suspend fun getRepositories(sortType: SearchRepositoriesApi.SortType,query: String,
                                perPage: Int, page: Int ): LiveData<MyResponse<RespositoryResponse>> {
        return MyResponse.createMyReponse {
            searchRepositoriesApi?.getRepositories(sortType, query, perPage, page)
        }
    }
}
