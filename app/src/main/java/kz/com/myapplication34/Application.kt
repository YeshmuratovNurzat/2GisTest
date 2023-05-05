package kz.com.myapplication34

import android.app.Application
import ru.dgis.sdk.Context
import ru.dgis.sdk.DGis
import ru.dgis.sdk.positioning.registerPlatformLocationSource
import ru.dgis.sdk.positioning.registerPlatformMagneticSource

class Application: Application() {
    lateinit var sdkContext: Context

    override fun onCreate() {
        super.onCreate()

        sdkContext = DGis.initialize(this)
    }

}