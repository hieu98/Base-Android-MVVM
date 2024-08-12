package com.example.baseandroidmvvm.di

import com.example.baseandroidmvvm.data.repository.SampleDatasource
import com.example.baseandroidmvvm.data.repository.SampleRemoteDatasource
import com.example.baseandroidmvvm.data.repository.SampleRepository
import org.koin.dsl.module

val repositoryModule = module {

    single {
        SampleRemoteDatasource()
    }
    single {
        SampleRepository(get())
    }
    single<SampleDatasource> {
        get<SampleRepository>()
    }
}