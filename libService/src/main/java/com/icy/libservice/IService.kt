package com.icy.libservice

interface IService{
    fun getId(): Int = ServiceIndex[ServiceIndex.getKey(this.javaClass)]
            ?: throw RuntimeException("Service has no id until it register!")
}