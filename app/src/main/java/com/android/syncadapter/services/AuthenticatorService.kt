package com.android.syncadapter.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.android.syncadapter.authenticator.Authenticator

class AuthenticatorService: Service() {
    lateinit var authenticator:Authenticator

    override fun onCreate() {
        authenticator = Authenticator(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return authenticator.iBinder
    }

}