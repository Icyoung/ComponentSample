package com.icy.libcommon.api

import android.content.Context
import com.icy.libservice.IService

interface ServiceB: IService{

    override val priority: Int
        get() = 2

    fun testB(callback:(argsFromB: String)-> Unit)

    fun goB(context: Context, args: String)
}