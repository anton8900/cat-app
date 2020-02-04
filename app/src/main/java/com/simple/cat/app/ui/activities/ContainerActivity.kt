package com.simple.cat.app.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import com.arellomobile.mvp.MvpAppCompatActivity
import com.google.android.material.navigation.NavigationView
import com.simple.cat.app.R
import com.simple.cat.app.ui.fragments.BaseFragment
import com.simple.cat.app.ui.fragments.LoadKittiesFragment
import com.simple.cat.app.ui.fragments.SavedKittiesFragment
import kotlinx.android.synthetic.main.activity_container.*

class ContainerActivity: MvpAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)

        setSupportActionBar(toolbar)

        openFragment(LoadKittiesFragment())

        setSupportActionBar(toolbar);
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.all_cats -> openFragment(LoadKittiesFragment())
            R.id.saved_cats -> openFragment(SavedKittiesFragment())
        }
        return true
    }

    fun openFragment(fragment: BaseFragment) {
        if(fragment is LoadKittiesFragment) {
            removeAllFragments()
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(fragment::class.java.canonicalName)
            .commit()
    }

    private fun removeAllFragments() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    /**
     * This function should go back
     * So if at least two fragments in the stack will be invoked previous fragment
     * If only one fragment in the stack activity will be finished
     */
    fun popBackStack() =
        if(supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }

    private fun openedFragment(): BaseFragment? =
        if(supportFragmentManager.fragments.size > 0)
            supportFragmentManager.fragments[supportFragmentManager.fragments.size - 1] as BaseFragment
        else
            null

    override fun onBackPressed() {
        val openedFragment = openedFragment()
        if (openedFragment is BaseFragment) {
            openedFragment.onBackPressed()
        }
    }

    fun setToolbar(title: String) {
        toolbar.title = title
    }
}