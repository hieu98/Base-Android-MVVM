package com.example.baseandroidmvvm.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.baseandroidmvvm.R

open class BaseFragment<T : ViewBinding> : Fragment() {

    var _binding: T? = null
    val binding: T get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open fun initView(){
        ResourcesCompat.getColor(resources, R.color.white, null).let {
            activity?.window?.statusBarColor = it
            activity?.window?.navigationBarColor = it
        }

    }

    open fun initObserver() = Unit
}