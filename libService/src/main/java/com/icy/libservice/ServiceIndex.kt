package com.icy.libservice

import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random

internal object ServiceIndex : ConcurrentHashMap<String, Int>() {

    fun <T: IService> getKey(serviceImplClass: Class<T>): String =
            serviceImplClass.interfaces[0].simpleName

    fun autoId(): Int = Random.nextInt(1000).let {
        if (ServiceIndex.containsValue(it)) {
            autoId()
        } else {
            it
        }
    }


}