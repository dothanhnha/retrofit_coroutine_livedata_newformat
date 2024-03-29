package com.example.retrofitcouroutinelivedatanewformat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.*
import com.example.retrofitcouroutinelivedatanewformat.Exception.ApiServerException
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
        viewModel.listRepositories1.observe(this, {
            Log.d("status_1",it.status.toString())
        })

        viewModel.listRepositories2.observe(this, {
            Log.d("status_2",it.status.toString())
        })

        viewModel.listRepositories3.observe(this, {
            Log.d("status_3",it.status.toString())
        })

        lifecycleScope.launch {
            viewModel.groupField(viewModel.listRepositories1.asFlow(),viewModel.listRepositories2.asFlow(), viewModel.listRepositories3.asFlow()).collect {
                Log.d("status_group",it.status.toString())
            }
        }

    }

    override fun onLoading() {

    }

    override fun onError() {

    }
}