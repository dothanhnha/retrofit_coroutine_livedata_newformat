package com.example.loadmoredemo

import androidx.lifecycle.*
import com.example.loadmoredemo.model.Repository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    val mainRepos: MainRepos
):BaseViewModel(){

    lateinit var currentWeather1: LiveData<MyResponse<List<Repository>>>

    override fun fetchData() {
        super.fetchData()
        viewModelScope.launch {
            currentWeather1 = mainRepos?.getRepositories(SearchRepositoriesApi.SortType.STAR,"android",1,1)
        }
    }

    override fun setLoadingField() {
        currentWeather1 = liveData {
            emit(MyResponse.loading())
        }
    }
}