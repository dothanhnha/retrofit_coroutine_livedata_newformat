package com.example.retrofitcouroutinelivedatanewformat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.retrofitcouroutinelivedatanewformat.model.Repository
import com.example.retrofitcouroutinelivedatanewformat.model.RespositoryResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SearchRepositoriesRepos @Inject constructor(
    val searchRepositoriesApi: SearchRepositoriesApi,
    val pagingRepos: PagingRepos
) {

    suspend fun getRepositories(sortType: SearchRepositoriesApi.SortType,query: String,
                                perPage: Int, page: Int ): LiveData<MyResponse<RespositoryResponse>> {
        return MyResponse.createMyReponse {
            searchRepositoriesApi?.getRepositories(sortType, query, perPage, page)
        }
    }


    /**
     * Search repositories whose names match the query, exposed as a stream of data that will emit
     * every time we get more data from the network.
     */
    fun getSearchResultStream(query: String): Flow<PagingData<Repository>> {
        Log.d("GithubRepository", "New query: $query")
        return Pager(
                config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = true),
                pagingSourceFactory = { pagingRepos}
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 5
    }
}
