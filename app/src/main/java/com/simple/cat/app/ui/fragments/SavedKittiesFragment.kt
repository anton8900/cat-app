package com.simple.cat.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.simple.cat.app.R
import com.simple.cat.app.model.Kitty
import com.simple.cat.app.mvp.presenter.SavedKittiesPresenter
import com.simple.cat.app.mvp.view.SavedKittiesView
import kotlinx.android.synthetic.main.fragment_kitty_load.*

class SavedKittiesFragment: KittyListFragment(), SavedKittiesView {
    @InjectPresenter
    lateinit var presenter: SavedKittiesPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_kitty_load, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar(getString(R.string.saved_kitties))
    }

    override fun onResume() {
        super.onResume()
        presenter.loadKitties()
    }

    override fun onPause() {
        super.onPause()
        presenter.release()
    }

    //region MVP

    override fun showKitties(kitties: List<Kitty>) {
        loadKittyList(kitties)
    }

    override fun toggleProgress(progressShowed: Boolean) {
        kitties_progress.visibility = if(progressShowed) View.VISIBLE else View.GONE
    }

    //end region
}