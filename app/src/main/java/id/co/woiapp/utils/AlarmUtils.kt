//package id.co.woiapp.utils
//
//import android.app.AlarmManager
//
//import android.app.PendingIntent
//import android.content.Context
//
//import android.content.Intent
//import java.util.*
//
//
//object AlarmUtils {
//    object AlarmUtils {
//        private const val REMINDER_8 = 8 * 60 * 60 * 1000
//        private const val REMINDER_12 = 12 * 60 * 60 * 1000
//        operator fun set(ctx: Context, reminder: Reminder) {
//            val timeReminder: Int = Integer.valueOf(reminder.getReminder())
//            val time: Array<String> = reminder.getStartingTime().split(":")
//            val calendar: Calendar = Calendar.getInstance()
//            calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(time[0]))
//            calendar.set(Calendar.MINUTE, Integer.valueOf(time[1]))
//            calendar.set(Calendar.SECOND, 0)
//            val currentTimeMillis: Long = Date().getTime()
//            if (currentTimeMillis > calendar.getTimeInMillis()) {
//                calendar.add(Calendar.HOUR_OF_DAY, timeReminder)
//            }
//            val alarmManager =
//                ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            val intent: Intent = Intent(ctx, AlarmReceiver::class.java)
//                .putExtra(AlarmReceiver.MEDICINE_EXTRA, reminder.getNamaObat())
//            val pendingIntent = PendingIntent.getBroadcast(
//                ctx, reminder.getId() as Int,
//                intent, 0
//            )
//            val repeatingTime =
//                if (timeReminder == 8) REMINDER_8 else REMINDER_12
//            alarmManager.setRepeating(
//                AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                repeatingTime.toLong(), pendingIntent
//            )
//        }
//
//        fun cancel(ctx: Context, reminder: Reminder) {
//            val intent: Intent = Intent(ctx, AlarmReceiver::class.java)
//                .putExtra(AlarmReceiver.MEDICINE_EXTRA, reminder.getNamaObat())
//            val pendingIntent = PendingIntent.getBroadcast(
//                ctx, reminder.getId() as Int,
//                intent, 0
//            )
//            val alarmManager =
//                ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            alarmManager.cancel(pendingIntent)
//        }
//    }
//}