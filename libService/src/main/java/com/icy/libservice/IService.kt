package com.icy.libservice

interface IService{

    val priority: Int
        get() = 0

    fun getId(): Int = ServiceIndex[ServiceIndex.getKey(this.javaClass)]
            ?: throw RuntimeException("Service has no id until it register!")
}