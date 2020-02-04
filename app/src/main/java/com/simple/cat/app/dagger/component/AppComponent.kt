package com.simple.cat.app.dagger.component

import com.simple.cat.app.dagger.module.*
import com.simple.cat.app.mvp.presenter.LoadKittiesPresenter
import com.simple.cat.app.mvp.presenter.SavedKittiesPresenter
import com.simple.cat.app.ui.fragments.BaseFragment
import javax.inject.Singleton
import dagger.Component

@Singleton
@Component(modules = [AppModule::class, NetModule::class, RepositoryModule::class, ServiceModule::class, ApiModule::class])
interface AppComponent {
    fun inject(baseFragment: BaseFragment)

    fun inject(baseFragment: LoadKittiesPresenter)

    fun inject(savedKittiesPresenter: SavedKittiesPresenter)
}
