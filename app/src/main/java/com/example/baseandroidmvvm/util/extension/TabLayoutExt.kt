package com.example.baseandroidmvvm.util.extension

import com.google.android.material.tabs.TabLayout

fun TabLayout.addTabSelectListener(
    onTabSelected: ((TabLayout.Tab?) -> Unit)? = null,
    onTabUnselected: ((TabLayout.Tab?) -> Unit)? = null,
    onTabReselected: ((TabLayout.Tab?) -> Unit)? = null
) {
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            onTabSelected?.invoke(tab)
        }
        override fun onTabUnselected(tab: TabLayout.Tab?) {
            onTabUnselected?.invoke(tab)
        }
        override fun onTabReselected(tab: TabLayout.Tab?) {
            onTabReselected?.invoke(tab)
        }
    })
}

