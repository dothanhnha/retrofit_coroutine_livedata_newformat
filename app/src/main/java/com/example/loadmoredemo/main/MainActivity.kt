package com.example.loadmoredemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.loadmoredemo.Exception.APIError
import com.example.loadmoredemo.Exception.ApiServerException
import kotlinx.android.synthetic.main.activity_main.*
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
        constraintLayout.setOnClickListener {
            viewModel.refreshData()
        }
    }


    override fun observeReponse() {
        viewModel.listRepositories.observe(this, Observer {
            if(it.status == MyStatus.SUCCESS)
                Log.d("test",it.data.toString())
            else if(it.status == MyStatus.ERROR){
                if(it.exception is ApiServerException)
                Log.d("test", it.exception.serverErrorMessage!!)
            }

        })
    }

    override fun onLoading() {

    }

    override fun onError() {

    }
}