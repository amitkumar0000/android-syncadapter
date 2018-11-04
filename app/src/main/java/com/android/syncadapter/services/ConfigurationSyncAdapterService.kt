package com.android.syncadapter.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.android.syncadapter.ConfigurationSyncAdapter
import java.util.*

class ConfigurationSyncAdapterService:Service(){

    var configurationSyncAdapter: ConfigurationSyncAdapter? = null
    var lock= Object()

    override fun onCreate() {
        synchronized(lock){
            if(configurationSyncAdapter == null)
                configurationSyncAdapter = ConfigurationSyncAdapter(applicationContext,true)
        }
    }


    override fun onBind(intent: Intent?): IBinder? {
        return configurationSyncAdapter?.syncAdapterBinder
    }

}