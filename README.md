# android-syncadapter
Sync Adapters were introduced with the intent of keeping Android devices and servers in sync. 
Syncs could be triggered from data changes in either the server or device, 
or by elapsed time and time of day.
The system will try to batch outgoing syncs to preserve battery life,
and transfers that are unable to run will queue up for transfer at a later time.
The system will attempt syncs only when the device is connected to a network.

Why to use synadapter in place of alarmManager
-------------------------------------------
Automated execution:-

     Sync can be requested based on variety of criteria, including data changes, elapsed time, or time of day.
     
Automated network checking :-

    Only run when ther is network.
    
Improved battery performance :- 

     Allows you to centralize all of your app’s data transfer tasks in one place, so that they all run at the same time. Your     data transfer is also scheduled in conjunction with data transfers from other apps. These factors reduce the number of         times the system has to switch on the network, which reduces battery usage.  
 
 Account management and authentication :-
  
        If your app requires user credentials or server login, you can optionally integrate account management and
        authentication into your data transfer.
        
Automatic Retry
     
     Failed syncs are retried with exponential backoffs.
     
For implementing SyncAdapter an Authenticator and ContentProvider is required.
------------------------------------------------------------------------------

Authenticator
==============
The sync adapter framework assumes that your sync adapter transfers data between 
device storage associated with an account and server storage that requires login access. 
For this reason, the framework expects you to provide a component called an authenticator as part of your sync adapter.
This component plugs into the Android accounts and authentication framework and 
provides a standard interface for handling user credentials such as login information.
 
ContentProvider
=================
Another thing that the Sync Adapter requires is a Content Provider even if you do not need/use one.

The sync adapter framework is designed to work with device data managed 
by the flexible and highly secure content provider framework. 
For this reason, the sync adapter framework expects that
an app that uses the framework has already defined a content provider for its local data.
If the sync adapter framework tries to run your sync adapter, 
and your app doesn’t have a content provider, your sync adapter crashes. 

Note the syncable flag in Provider tag in manifest file

This indicates that the provider allows the sync adapter to make data transfers with it,
but only if explicitly done so.

AbstractThreadSynAdapter
==========================

Your sync adapter must implement the AbstractThreadedSyncAdapter, 
the actual background operations happen inside the onPerformSync method. 
This method gets called automatically when the syncing is supposed to occur.
If you haven’t figured this out by now, the entire sync adapter runs on a background thread, 
so there is no need to add additional background processing in here.


Now that we have a way to handle the background operation,
we need to give the sync adapter access to our code/information. 
We do this by creating another service that passes a special Android binder object from the sync adapter to the framework.
     
Note: The attribute android:process=":sync" tells the system to run the Service in a global shared process named sync. 
If you have multiple sync adapters in your app they can share this process, which reduces overhead.    
   
We can automatically run a sync adapter
=======================================
When server data changes 
when device data changes 
at regular intervals
or on demand.   


Run the sync adapter when server data changes
================================================
If your app transfers data from a server and the server data changes frequently,
you can use a sync adapter to do downloads in response to data changes.
To run the sync adapter, have the server send a special message to a BroadcastReceiver in your app.
In response to this message, call ContentResolver.requestSync() 
to signal the sync adapter framework to run your sync adapter.

Google Cloud Messaging (GCM) provides both the server and device components you need to make this messaging system work. 
Using GCM to trigger transfers is more reliable and more efficient than polling servers for status.
While polling requires a Service that is always active, 
GCM uses a BroadcastReceiver that's activated when a message arrives. 
While polling at regular intervals uses battery power even if no updates are available, 
GCM only sends messages when needed.

class GcmBroadcastReceiver : BroadcastReceiver() {

    ...
    override fun onReceive(context: Context, intent: Intent) {
    
        // Get a GCM object instance
        val gcm: GoogleCloudMessaging = GoogleCloudMessaging.getInstance(context)
        
        // Get the type of GCM message
        val messageType: String? = gcm.getMessageType(intent)
        
        /*
         * Test the message type and examine the message contents.
         * Since GCM is a general-purpose messaging system, you
         * may receive normal messages that don't require a sync
         * adapter run.
         * The following code tests for a a boolean flag indicating
         * that the message is requesting a transfer from the device.
         */
         
        if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE == messageType
        
            && intent.getBooleanExtra(KEY_SYNC_REQUEST, false)) {
            
            /*
             * Signal the framework to run your sync adapter. Assume that
             * app initialization has already created the account.
             */
             
            ContentResolver.requestSync(mAccount, AUTHORITY, null)
            ...
            
        }
        ...
        
    }
    ...    
}

Run the sync adapter when content provider data changes
======================================================
If your app collects data in a content provider, 
and you want to update the server whenever you update the provider, 
you can set up your app to run your sync adapter automatically. 
To do this, you register an observer for the content provider. 
When data in your content provider changes, the content provider framework calls the observer. 
In the observer, call requestSync() to tell the framework to run your sync adapter.

Run the sync adapter periodically
==================================
  addPeriodicSync()

Run the sync adapter on demand
================================
ContentResolver.requestSync()


  
  
  


