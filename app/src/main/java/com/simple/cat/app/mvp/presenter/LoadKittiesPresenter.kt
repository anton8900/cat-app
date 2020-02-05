package com.simple.cat.app.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.simple.cat.app.App
import com.simple.cat.app.R
import com.simple.cat.app.model.Kitty
import com.simple.cat.app.mvp.view.LoadKittiesView
import com.simple.cat.app.service.kitty.KittyService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@InjectViewState
class LoadKittiesPresenter: MvpPresenter<LoadKittiesView>() {
    @Inject
    lateinit var kittyService: KittyService

    private var page = 0

    private var kittyDisposables: CompositeDisposable? = null

    private val kittiesLoading = AtomicBoolean(false)

    init {
        App.AppComponent?.inject(this)
    }

    override fun attachView(view : LoadKittiesView) {
        super.attachView(view)
        kittyDisposables = CompositeDisposable()
        loadKitties(false)
    }

    override fun detachView(view : LoadKittiesView) {
        super.detachView(view)

        kittyDisposables?.dispose()
        kittyDisposables = null
    }

    private fun loadKitties(addMoreKitties: Boolean) {
        val kittySaveDisposable = kittyDisposables
        if(!kittiesLoading.get() && kittySaveDisposable != null) {
            page++
            kittiesLoading.set(true)
            viewState.toggleProgress(true)
            kittySaveDisposable.add(kittyService.loadKitties(page)
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
            )
        }
    }

    fun saveKitty(kitty: Kitty) {
        kittyDisposables?.add(
            kittyService.saveCat(kitty)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    viewState.showToast(R.string.kitty_saved)
                }
        )
    }

    fun addMoreKitties() {
        loadKitties(true)
    }
}