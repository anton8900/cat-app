package com.simple.cat.app.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.simple.cat.app.R

class SplashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()

        startActivity(Intent(this, ContainerActivity::class.java))
        finish()
    }
}