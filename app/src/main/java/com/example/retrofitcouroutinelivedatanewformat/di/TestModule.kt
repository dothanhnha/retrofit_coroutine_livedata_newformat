package com.example.retrofitcouroutinelivedatanewformat.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(ActivityComponent::class)
class TestModule {

    @Provides
    @ActivityScoped
    fun provideSomething(
    ): Something {
        return Something()
    }
}