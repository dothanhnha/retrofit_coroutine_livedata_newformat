package com.example.loadmoredemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class MainActivity : AppCompatActivity(),
    BaseViewModel.OnStatusResponseChange {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<MainViewModel> { viewModelFactory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as MyApplication).appComponent.mainComponent().create()
            .inject(this)
        viewModel.setup(this, this)
        viewModel.refreshData()

    }

    override fun observeReponse() {
        viewModel.currentWeather1.observe(this, Observer {
            Log.d("test",it.toString())
        })
    }

    override fun onSuccessAll() {
        Log.d("test",viewModel.currentWeather1.value.toString())
    }

    override fun onLoading() {

    }

    override fun onError() {

    }
}