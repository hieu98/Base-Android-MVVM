package com.example.baseandroidmvvm.util.extension

import com.example.baseandroidmvvm.viewmodel.BaseViewModel
import io.reactivex.rxjava3.disposables.Disposable

fun Disposable.addTo(viewModel: BaseViewModel) {
    viewModel.compositeDisposable.add(this)
}