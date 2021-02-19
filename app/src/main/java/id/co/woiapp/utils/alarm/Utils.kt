package id.co.woiapp.utils.alarm


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

object Utils {

    fun setAlarm(context: Context, timeOfAlarm: Long,idTask : Int,title:String,desc:String) {

        // Intent to start the Broadcast Receiver
        val broadcastIntent = Intent(context
            , NotificationReceiver::class.java)
        broadcastIntent.putExtra("MESSAGE_TITLE",title)
        broadcastIntent.putExtra("MESSAGE_DESC",desc)

        val pIntent = PendingIntent.getBroadcast(
            context,
            idTask,
            broadcastIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        // Setting up AlarmManager
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (System.currentTimeMillis() < timeOfAlarm) {
            alarmMgr.set(
                AlarmManager.RTC_WAKEUP,
                timeOfAlarm,
                pIntent
            )
        }
    }

    fun cancelAlarm(context: Context, idTask : Int){
        val broadcastIntent = Intent(context
            , NotificationReceiver::class.java)

        val pIntent = PendingIntent.getBroadcast(
            context,
            idTask,
            broadcastIntent,
            0
        )
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmMgr.cancel(pIntent)
    }
}