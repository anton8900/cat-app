package com.simple.cat.app

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import com.simple.cat.app.dagger.component.AppComponent
import com.simple.cat.app.dagger.module.*
import com.facebook.stetho.Stetho
import com.raizlabs.android.dbflow.config.FlowManager
import com.simple.cat.app.dagger.component.DaggerAppComponent

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }

        //set locale
        //LanguageUtils.changeLanguage(Locale(getSharedPreferences("context", Context.MODE_PRIVATE).getString("language", "en")), resources)

        //dagger
        AppComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .netModule(NetModule())
            .repositoryModule(RepositoryModule())
            .serviceModule(ServiceModule())
            .apiModule(ApiModule())
            .build()

        //dbflow
        FlowManager.init(this)

        //exception handler
        Thread.setDefaultUncaughtExceptionHandler { paramThread: Thread, paramThrowable: Throwable ->
            uncaughtExceptionHandler(paramThrowable)
        }

        if (BuildConfig.DEBUG) {
            StrictMode.setVmPolicy(
                VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
        }
    }

    private fun uncaughtExceptionHandler(paramThrowable: Throwable) {
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    companion object {
        var AppComponent: AppComponent? = null
    }
}