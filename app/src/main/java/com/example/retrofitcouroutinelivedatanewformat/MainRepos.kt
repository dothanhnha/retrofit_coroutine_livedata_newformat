package com.example.retrofitcouroutinelivedatanewformat

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.retrofitcouroutinelivedatanewformat.model.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MainRepos @Inject constructor(
    val searchRepositoriesRepos: SearchRepositoriesRepos
) {


    suspend fun getRepositories(
        sortType: SearchRepositoriesApi.SortType, query: String,
        perPage: Int, page: Int
    ): LiveData<MyResponse<List<Repository>>> {
        return searchRepositoriesRepos.getRepositories(sortType, query, perPage, page)
                .map { MyResponse(it.status, it.data?.items, it.exception) }
    }
}