package com.icy.libservice

object ServiceProvider: IProvider<IService> {

    @Suppress("UNCHECKED_CAST")
    override fun <T : IService> produce(clazz: Class<T>): T {
        val serviceId = ServiceIndex[clazz.simpleName]?: throw RuntimeException("Service is unregister!")
        return ServiceRegistry.getService(serviceId) as T
    }

}