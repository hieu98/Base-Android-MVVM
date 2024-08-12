package com.example.baseandroidmvvm.util.extension

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import com.example.baseandroidmvvm.R

fun Fragment.navigate(
    destination: Int,
    extraData: Bundle? = null
) {
    activity?.let {
        try {
            Navigation.findNavController(it, R.id.nav_host_fragment)
                .navigate(destination, extraData, navOptions {
                    anim {
                        enter = R.anim.anim_slide_in_right
                        exit = R.anim.anim_slide_out_left
                        popEnter = R.anim.anim_slide_in_left
                        popExit = R.anim.anim_slide_out_right
                    }
                })
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}

fun Fragment.onBackPressed(runnable: Runnable) {
    activity?.onBackPressedDispatcher?.addCallback(
        viewLifecycleOwner,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                runnable.run()
            }
        })
}

fun Fragment.popBackStack(destination: Int? = null) {
    try {
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        if (destination != null) navController.popBackStack(destination, false)
        else navController.popBackStack()
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

fun DialogFragment.setWidthPercent(percentage: Int) {
    val percent = percentage.toFloat() / 100
    val dm = Resources.getSystem().displayMetrics
    val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
    val percentWidth = rect.width() * percent
    dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
}

fun Fragment.showDialog(dialogFragment: DialogFragment, tag: String? = null) {
    if (isAdded && !isDetached && activity != null) {
        try {
            dialogFragment.show(this.childFragmentManager, tag)
        } catch (ex: IllegalStateException) {
            ex.printStackTrace()
        }
    }
}