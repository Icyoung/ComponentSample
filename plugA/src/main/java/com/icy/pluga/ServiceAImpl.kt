package com.icy.pluga

import com.icy.libcommon.api.ServiceA

class ServiceAImpl: ServiceA{
    override fun testA(): String {
        return "PlugA: I am plugA"
    }

}