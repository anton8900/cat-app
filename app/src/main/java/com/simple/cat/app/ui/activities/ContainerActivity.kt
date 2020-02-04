package com.simple.cat.app.ui.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import com.arellomobile.mvp.MvpAppCompatActivity
import com.simple.cat.app.R
import com.simple.cat.app.ui.fragments.BaseFragment
import com.simple.cat.app.ui.fragments.LoadKittiesFragment
import com.simple.cat.app.ui.fragments.SavedKittiesFragment
import kotlinx.android.synthetic.main.activity_container.*
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.ContextCompat


class ContainerActivity: MvpAppCompatActivity() {
    private val writePermissionRequest = 1

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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            writePermissionRequest -> {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    val url = lastUrl
                    if(url != null) {
                        save(url)
                        lastUrl = null
                    }
                }
            }
        }
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

    fun saveImage(url: String) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            save(url)
        } else {
            lastUrl = url
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), writePermissionRequest)
        }
    }

    private fun save(url: String) {
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(Uri.parse(url))
        request.setDestinationInExternalPublicDir( Environment.DIRECTORY_PICTURES, url);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        downloadManager.enqueue(request)
    }

    companion object {
        private var lastUrl: String? = null
    }
}