package com.icy.libcommon

import android.content.Context
import com.icy.librouter.annotation.Bundle
import com.icy.librouter.annotation.TargetClassName

interface RouterApi{

    @TargetClassName("com.icy.pluga.AActivity")
    fun goA(context: Context)

    @TargetClassName("com.icy.plugb.BActivity")
    fun goB(context: Context,@Bundle("args") argsA: String)
}