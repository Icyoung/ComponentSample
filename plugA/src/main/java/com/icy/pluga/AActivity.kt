package com.icy.pluga

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.icy.libcommon.RouterApi
import com.icy.libcommon.api.ServiceB
import com.icy.librouter.Router
import com.icy.librouter.RouterFactory
import com.icy.librouter.config.UriConfig
import com.icy.libservice.ServiceProvider
import kotlinx.android.synthetic.main.activity_a.*

class AActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)

        val serviceB = ServiceProvider.produce(ServiceB::class.java)

        btn_test_api_from_b.setOnClickListener {
            serviceB.testB{argsFromB ->  doSomeThing(argsFromB)}
        }

        btn_go_b_using_router.setOnClickListener {
           val routerApi =  RouterFactory.createRouterService(
                Router.Builder().build(), RouterApi::class.java)
            routerApi.goB(this@AActivity,"AAA From Router")
        }

        btn_go_b_using_service.setOnClickListener {
            serviceB.goB(this@AActivity,"AAA From Service")
        }

    }

    fun doSomeThing(argsFromB: String){
        Toast.makeText(this,"A:doSomeThing($argsFromB)", Toast.LENGTH_LONG).show()
    }
}
