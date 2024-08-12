package com.example.baseandroidmvvm.util.extension

import androidx.viewpager2.widget.ViewPager2

fun ViewPager2.setPageChangeListener(
    onPageSelected: (Int) -> Unit
) {
    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        var previousPosition = 0

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            if (previousPosition != position) {
                onPageSelected.invoke(position)
                previousPosition = position
            }
        }
    })
}

fun ViewPager2.nextPage() {
    val page = currentItem
    setCurrentItem(page + 1, true)
}