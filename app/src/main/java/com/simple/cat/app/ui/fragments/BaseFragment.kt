package com.simple.cat.app.ui.fragments

import android.app.Application
import com.arellomobile.mvp.MvpAppCompatFragment
import com.simple.cat.app.App
import com.simple.cat.app.ui.activities.ContainerActivity
import javax.inject.Inject

open class BaseFragment: MvpAppCompatFragment() {
    @Inject
    lateinit var application: Application

    init {
        App.AppComponent?.inject(this)
    }

    fun openFragment(fragment: BaseFragment) {
        val activity = activity
        if(activity is ContainerActivity) {
            activity.openFragment(fragment)
        }
    }

    protected fun goBack() {
        val activity = activity
        if(activity is ContainerActivity) {
            activity.popBackStack()
        }
    }

    /**
     * Open function onBackPressed()
     * Default action just go back
     * You need to override it if you need to do something else
     */
    open fun onBackPressed() {
        goBack()
    }

    protected fun setToolbar(title: String) {
        val activity = activity
        if(activity is ContainerActivity) {
            activity.setToolbar(title)
        }
    }
}