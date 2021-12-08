package com.vipulasri.posty

import android.app.Application
import timber.log.Timber

/**
 * Created by Vipul Asri on 08/12/21.
 */

class PostyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }

}