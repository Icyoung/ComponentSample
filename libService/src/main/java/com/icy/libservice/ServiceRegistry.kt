package com.icy.libservice

import android.util.SparseArray

object ServiceRegistry{

    private val services: SparseArray<IService> by lazy { SparseArray<IService>() }

    fun register(serviceImpl: IService){
        val sidKey = ServiceIndex.getKey(serviceImpl.javaClass)
        val sid = ServiceIndex.autoId()
        if(ServiceIndex[sidKey] == null  && services.get(sid) == null){
            services.put(sid,serviceImpl)
            ServiceIndex.put(sidKey,sid)
        }else{
            throw RuntimeException("Don't register service $sidKey repeat!")
        }
    }

    fun registerArray(vararg serviceImplClasses: Class<out IService>){
        serviceImplClasses.map { it.newInstance() }
            .sortedBy { it.priority }
            .forEach{ register(it)}
    }

    internal fun getService(serviceId: Int): IService {
        return services.get(serviceId)?: throw RuntimeException("Service id: $serviceId is unregister!")
    }
}