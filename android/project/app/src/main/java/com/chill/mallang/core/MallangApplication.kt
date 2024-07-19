package com.chill.mallang.core

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MallangApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}