package com.example.loadmoredemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.example.loadmoredemo.model.Repository
import com.example.loadmoredemo.model.RespositoryResponse
import javax.inject.Inject

class MainRepos @Inject constructor(
        val searchRepositoriesRepos: SearchRepositoriesRepos) {


    suspend fun getRepositories(sortType: SearchRepositoriesApi.SortType, query: String,
                                perPage: Int, page: Int): LiveData<MyResponse<List<Repository>>> {
        return searchRepositoriesRepos.getRepositories(sortType, query, perPage, page).map { MyResponse(it.status, it.data?.items, it.message) }
    }
}