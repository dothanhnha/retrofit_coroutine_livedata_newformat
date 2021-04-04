package com.example.retrofitcouroutinelivedatanewformat

import androidx.lifecycle.*
import com.example.retrofitcouroutinelivedatanewformat.model.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    val mainRepos: MainRepos
):BaseViewModel(){

    lateinit var listRepositories1: LiveData<MyResponse<List<Repository>>>
    lateinit var listRepositories2: LiveData<MyResponse<List<Repository>>>
    lateinit var listRepositories3: LiveData<MyResponse<List<Repository>>>

    override fun fetchData() {
        viewModelScope.launch {
            listRepositories1 = mainRepos?.getRepositories(SearchRepositoriesApi.SortType.STAR,"android",3,1)
            listRepositories2 = mainRepos?.getRepositories(SearchRepositoriesApi.SortType.STAR,"list",30,1)
            listRepositories3 = mainRepos?.getRepositories(SearchRepositoriesApi.SortType.STAR,"kotlin",15,1)
        }
    }
}