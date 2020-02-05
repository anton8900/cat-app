package com.simple.cat.app.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.simple.cat.app.App
import com.simple.cat.app.mvp.view.SavedKittiesView
import com.simple.cat.app.service.kitty.KittyService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@InjectViewState
class SavedKittiesPresenter : MvpPresenter<SavedKittiesView>() {
    @Inject
    lateinit var kittyService: KittyService

    private var kittiesLoadDisposable: Disposable? = null

    init {
        App.AppComponent?.inject(this)
    }

    fun loadKitties() {
        viewState.toggleProgress(true)
        kittiesLoadDisposable = kittyService.findAllCats()
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally{ viewState.toggleProgress(false) }
            .subscribe{ viewState.showKitties(it) }
    }

    fun release() {
        kittiesLoadDisposable?.dispose()
    }

}