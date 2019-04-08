package com.icy.libcommon.api

import com.icy.libservice.IService

interface ServiceA: IService{

    override val priority: Int
        get() = 1

    fun testA(): String
}