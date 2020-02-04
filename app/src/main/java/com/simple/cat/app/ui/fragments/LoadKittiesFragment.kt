package com.simple.cat.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.simple.cat.app.R
import com.simple.cat.app.model.Kitty
import com.simple.cat.app.mvp.presenter.LoadKittiesPresenter
import com.simple.cat.app.mvp.view.LoadKittiesView
import com.simple.cat.app.ui.lists.KittyListAdapter
import kotlinx.android.synthetic.main.fragment_kitty_load.*

class LoadKittiesFragment: KittyListFragment(), LoadKittiesView {
    @InjectPresenter
    lateinit var kittiesPresenter: LoadKittiesPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_kitty_load, container, false)
    }

    override fun onResume() {
        super.onResume()

        kittiesPresenter.loadKitties(false)
    }

    override fun onPause() {
        super.onPause()

        kittiesPresenter.release()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        kittyList?.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    kittiesPresenter.loadKitties(true)
                }
            }
        })

        setToolbar(getString(R.string.all_kitties))
    }

    //region MVP

    override fun showKitties(kitties: List<Kitty>) {
        loadKittyList(kitties, View.OnClickListener { view ->
            val adapter = kittyList?.adapter
            if(adapter is KittyListAdapter) {
                val kitty = adapter.getKittyAt(kittyList?.getChildLayoutPosition(view))
                if(kitty != null) {
                    kittiesPresenter.saveKitty(kitty)
                }
            }
        })
    }

    override fun addKities(kitties: List<Kitty>) {
        loadMoreKitties(kitties)
    }

    override fun toggleProgress(progressShowed: Boolean) {
        kitties_progress.visibility = if(progressShowed) View.VISIBLE else View.GONE
    }

    override fun showToast(message: Int) {
        Toast.makeText(application, message, Toast.LENGTH_SHORT).show()
    }

    //end region
}