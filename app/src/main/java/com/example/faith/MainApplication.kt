package com.example.faith

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
/**
 * @author Remi Mestdagh
 */
@HiltAndroidApp
open class MainApplication : Application() {
    open fun getBaseUrl() = "http://192.168.1.37:45455/api/"
}

