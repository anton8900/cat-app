package com.simple.cat.app.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.simple.cat.app.App
import com.simple.cat.app.R
import com.simple.cat.app.model.Kitty
import com.simple.cat.app.mvp.view.LoadKittiesView
import com.simple.cat.app.server.api.KittyApi
import com.simple.cat.app.service.kitty.KittyService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@InjectViewState
class LoadKittiesPresenter: MvpPresenter<LoadKittiesView>() {
    @Inject
    lateinit var kittyService: KittyService

    @Inject
    lateinit var kittyApi: KittyApi

    private val getKittiesThread = Executors.newFixedThreadPool(1)

    private var page = 0
    private val pageSize = 10

    private var kittySaveDisposable: Disposable? = null
    private var kittyLoadDisposable: Disposable? = null

    private val kittiesLoading = AtomicBoolean(false)

    init {
        App.AppComponent!!.inject(this)
    }

    fun saveKitty(kitty: Kitty) {
        kittySaveDisposable = kittyService.saveCat(kitty)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                viewState.showToast(R.string.kitty_saved)
            }
    }

    fun loadKitties(addMoreKitties: Boolean) {
        if(!kittiesLoading.get()) {
            page++
            kittiesLoading.set(true)
            viewState.toggleProgress(true)
            kittyLoadDisposable = kittyApi.getKitties(pageSize, page)
                .subscribeOn(Schedulers.from(getKittiesThread))
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    viewState.toggleProgress(false)
                    kittiesLoading.set(false)
                }
                .subscribe (
                    {
                        if(addMoreKitties) {
                            viewState.addKities(it)
                        } else {
                            viewState.showKitties(it)
                        }
                    },
                    { viewState.showToast(R.string.something_went_wrong) }
                )
        }
    }

    fun release() {
        kittySaveDisposable?.dispose()
        kittyLoadDisposable?.dispose()
    }
}