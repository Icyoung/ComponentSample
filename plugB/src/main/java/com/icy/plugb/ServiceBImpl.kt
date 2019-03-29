package com.icy.plugb


import android.content.Context
import android.content.Intent
import android.util.Log
import com.icy.libcommon.api.ServiceB

class ServiceBImpl: ServiceB{

    fun provideArgs1(): String{
        return "args1 From B"
    }

    fun doSomeThing(): Boolean{
        Log.d("TAG","B:doSomeThing")
        return true
    }

    override fun testB(callback:(argsFromB: String)-> Unit) {
        if(doSomeThing()){
            callback(provideArgs1())
        }
    }

    override fun goB(context: Context, args: String) {
        context.startActivity(Intent(context,BActivity::class.java).apply { putExtra("args",args) })
    }





}