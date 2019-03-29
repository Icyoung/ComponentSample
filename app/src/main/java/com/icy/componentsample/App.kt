package com.icy.componentsample

import android.app.Application
import com.icy.libservice.ServiceRegistry
import com.icy.pluga.ServiceAImpl
import com.icy.plugb.ServiceBImpl

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        ServiceRegistry.registerArray(ServiceAImpl::class.java,ServiceBImpl::class.java)
    }

}