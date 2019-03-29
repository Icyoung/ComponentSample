package com.icy.libservice

interface IProvider<T>{
    fun <K: T> produce(clazz: Class<K>): K
}