package com.simple.cat.app.mvp.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.simple.cat.app.model.Kitty

@StateStrategyType(OneExecutionStateStrategy::class)
interface LoadKittiesView: MvpView {
    fun showKitties(kitties: List<Kitty>)
    fun showToast(message: Int)
    fun toggleProgress(progressShowed: Boolean)
    fun addKities(kitties: List<Kitty>)
}