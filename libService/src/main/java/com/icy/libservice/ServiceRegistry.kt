package com.icy.libservice

import android.util.SparseArray

object ServiceRegistry{

    private val services: SparseArray<IService> by lazy { SparseArray<IService>() }

    fun register(serviceImplClass: Class<out IService>){
        val service = serviceImplClass.newInstance()
        val sidKey = ServiceIndex.getKey(serviceImplClass)
        val sid = ServiceIndex.autoId()
        if(ServiceIndex[sidKey] == null  && services.get(sid) == null){
            services.put(sid,service)
            ServiceIndex.put(sidKey,sid)
        }else{
            throw RuntimeException("Don't register service $sidKey repeat!")
        }
    }

    fun registerArray(vararg serviceImplClasses: Class<out IService>){
        serviceImplClasses.forEach{ register(it)}
    }

    internal fun getService(serviceId: Int): IService {
        return services.get(serviceId)?: throw RuntimeException("Service id: $serviceId is unregister!")
    }
}