package com.example.loadmoredemo

import androidx.lifecycle.*
import com.example.loadmoredemo.model.Repository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    val mainRepos: MainRepos
):BaseViewModel(){

    lateinit var listRepositories: LiveData<MyResponse<List<Repository>>>

    override fun fetchData() {
        viewModelScope.launch {
            listRepositories = mainRepos?.getRepositories(SearchRepositoriesApi.SortType.STAR,"",110,-1)
        }
    }
}