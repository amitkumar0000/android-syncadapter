package com.android.syncadapter

import android.accounts.Account
import android.content.AbstractThreadedSyncAdapter
import android.content.ContentProviderClient
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SyncResult
import android.os.Bundle
import android.util.Log

class ConfigurationSyncAdapter(context: Context,autoInitialize:Boolean) : AbstractThreadedSyncAdapter(context,autoInitialize) {
    override fun onPerformSync(account: Account?, extras: Bundle?, authority: String?,
                               provider: ContentProviderClient?, syncResult: SyncResult?) {
        Log.i(TAG, "onPerformSync() was called");

        /* This is where you would put any code you want to run in the background.
           Such as fetching data from a server! */
//        manager.fetchDataFromServer();
    }

}