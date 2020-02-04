package com.simple.cat.app.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simple.cat.app.R
import com.simple.cat.app.model.Kitty
import com.simple.cat.app.ui.lists.KittyListAdapter

open class KittyListFragment() : BaseFragment() {
    var kittyList: RecyclerView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        kittyList = view.findViewById(R.id.kitty_list)

        if(kittyList is RecyclerView) {
            kittyList?.layoutManager = LinearLayoutManager(application)
        }
    }

    protected fun loadKittyList(kitties: List<Kitty>, onClickListener: View.OnClickListener? = null) {
        val activity = activity
        if(activity != null) {
            kittyList?.visibility = View.VISIBLE
            kittyList?.adapter = KittyListAdapter(activity, ArrayList(kitties), onClickListener)
        }
    }

    protected fun loadMoreKitties(kitties: List<Kitty>) {
        val adapter = kittyList?.adapter
        if(adapter is KittyListAdapter) {
            adapter.loadMoreKitties(kitties)
        }
    }
}