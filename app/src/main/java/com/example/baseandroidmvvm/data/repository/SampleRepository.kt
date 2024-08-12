package com.example.baseandroidmvvm.data.repository

interface SampleDatasource {

}

class SampleRemoteDatasource() : SampleDatasource {

}

class SampleRepository(
    private val sampleRemoteDatasource: SampleRemoteDatasource
) : SampleDatasource {

}