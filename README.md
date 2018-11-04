# android-syncadapter
Sync Adapters were introduced with the intent of keeping Android devices and servers in sync. 
Syncs could be triggered from data changes in either the server or device, 
or by elapsed time and time of day.
The system will try to batch outgoing syncs to preserve battery life,
and transfers that are unable to run will queue up for transfer at a later time.
The system will attempt syncs only when the device is connected to a network.
