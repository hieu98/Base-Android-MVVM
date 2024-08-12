package com.example.baseandroidmvvm.di

import com.example.baseandroidmvvm.viewmodel.SampleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SampleViewModel(get()) }
}