package com.icy.plugb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.icy.libcommon.api.ServiceA
import com.icy.libservice.ServiceProvider
import kotlinx.android.synthetic.main.activity_b.*

class BActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)

        tv_from_a.text = intent.getStringExtra("args")

        btn_test_api_from_a.setOnClickListener {
            val serviceA = ServiceProvider.produce(ServiceA::class.java)
            Toast.makeText(this,serviceA.testA(),Toast.LENGTH_LONG).show()
        }
    }
}
