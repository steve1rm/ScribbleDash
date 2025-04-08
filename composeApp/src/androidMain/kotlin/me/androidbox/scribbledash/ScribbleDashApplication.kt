package me.androidbox.scribbledash

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class ScribbleDashApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initializeKoin(
            config = {
                androidContext(this@ScribbleDashApplication)
                androidLogger(Level.DEBUG)
            },
        )
    }
}