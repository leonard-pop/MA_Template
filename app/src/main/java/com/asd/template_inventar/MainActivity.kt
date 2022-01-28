package com.asd.template_inventar

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.asd.template_inventar.model.domain.Product
import com.asd.template_inventar.model.viewmodel.ProductsViewModel
import com.asd.template_inventar.navigation.Navigation
import com.asd.template_inventar.ui.theme.TemplateTheme
import com.asd.template_inventar.utils.*

@AndroidEntryPoint
class MainActivity : ComponentActivity(), MessageListener {
    private var lastState = InternetStatus.OFFLINE
    val viewModel : ProductsViewModel by viewModels()

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectToSocket()
        bcontext = applicationContext

        setContent {
            TemplateTheme {
                Surface(color = MaterialTheme.colors.background) {
                    App()
                }
            }
        }

        initReceiver()
    }

    private fun connectToSocket() {
        WebSocketManager.init(baseUrl, this)
        WebSocketManager.connect(WebSocketManager.createListener())
    }

    private fun initReceiver() {
        InternetStatusReceiver.setOnCallbackReceivedListener(object : InternetCallback {
            override fun onStatusChanged(status: InternetStatus) {
                if(status!=lastState) {
                    when (status) {
                        InternetStatus.ONLINE -> {
                            Log.d("Debug", "Internet status: ONLINE")
                            CoroutineScope(Dispatchers.IO).launch {
                                viewModel.syncLocalData()
                            }
                        }
                        InternetStatus.OFFLINE -> {
                            Log.d("Debug", "Internet status: OFFLINE")
                        }
                    }

                    lastState=status
                }
            }
        })

        registerReceiver(
            InternetStatusReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    companion object {
        lateinit var bcontext: Context
        val baseUrl = "http://192.168.0.101:2025/"
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(InternetStatusReceiver)
    }

    override fun onConnectSuccess() {
        Log.d("Debug","Socket connected")
    }

    override fun onConnectFailed() {
        Log.d("Debug","Socket connection failed")
    }

    override fun onClose() {
        Log.d("Debug","Socket closed")
    }

    override fun onMessage(text: String?) {
        Log.d("Debug","Message received: $text")

        val entity = Gson().fromJson<Product>(text, Product::class.java)
        runOnUiThread {
            Toast.makeText(this, "Server broadcast: $entity", Toast.LENGTH_SHORT).show()
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun App(){
    Navigation()
}