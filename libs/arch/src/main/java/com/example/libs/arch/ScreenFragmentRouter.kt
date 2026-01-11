package com.example.libs.arch

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import kotlin.reflect.KClass

class ScreenFragmentRouter(
    val title: Int,
    val fragmentType: KClass<out Fragment>
) {
    fun launch(
        activity: FragmentActivity,
        addToBackStack: Boolean = true,
        bundle: Bundle? = null,
    ) {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragmentType.java, bundle)
            .addToBackStack(addToBackStack)
            .commitAllowingStateLoss()
    }

    private fun FragmentTransaction.addToBackStack(value: Boolean): FragmentTransaction {
        return when {
            value -> addToBackStack(null)
            else -> this
        }
    }
}
