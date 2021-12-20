package com.marmutech.ramdantimetable.ramadantimetable.ui.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import timber.log.Timber


class NetworkObserver(
    private val connectivityManager: ConnectivityManager,
    private val context: Context
) : LifecycleObserver {

    private var monitoringConnectivity = false

    private val _connectedStatus = MutableLiveData<Boolean>()
    val connectedStatus: LiveData<Boolean>
        get() = _connectedStatus

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private val connectivityCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _connectedStatus.postValue(true)
            // we are connected, so we can stop listening
            connectivityManager.unregisterNetworkCallback(this)
            monitoringConnectivity = false
        }

        override fun onLost(network: Network) {
            _connectedStatus.postValue(false)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopMonitoringConnectivity() {
        Timber.d("ON_PAUSE")
        Timber.d("monitoringConnectivity $monitoringConnectivity")
        if (monitoringConnectivity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                connectivityManager.unregisterNetworkCallback(connectivityCallback)
            } else {
                Timber.d("unregisterReceiver broadcast")
                context.unregisterReceiver(networkBroadcast)
            }
            monitoringConnectivity = false
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startMonitoringConnectivity() {
        Timber.d("ON_RESUME")
        val connected = isOnline()
        _connectedStatus.postValue(connected)
        if (!connected) {
            // we don't have internet connection, so we listen to notifications in connection status
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Timber.d("register callback")
                connectivityManager.registerNetworkCallback(
                    NetworkRequest.Builder()
                        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build(),
                    connectivityCallback
                )
            } else {
                Timber.d("register broadcast")
                registerNetworkBroadcast()
            }
            monitoringConnectivity = true
        }
    }

    private fun isOnline(): Boolean {
        val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private val networkBroadcast = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Timber.d("networkBroadcast onReceive")
            val isConnected = isOnline()
            _connectedStatus.value = isConnected
//            if (isConnected) {
//                monitoringConnectivity = false
//
//            }
        }
    }

    private fun registerNetworkBroadcast() {
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(networkBroadcast, filter)
    }
}
