package com.simple.cat.app.mvp.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.simple.cat.app.model.Kitty

@StateStrategyType(OneExecutionStateStrategy::class)
interface SavedKittiesView: MvpView{

    fun showKitties(kitties: List<Kitty>)
    fun toggleProgress(progressShowed: Boolean)
}