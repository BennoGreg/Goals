package at.fhooe.mc.goals.ui.newGoal.Reminder

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import at.fhooe.mc.goals.R

object NotificationHelper {


    fun createNotificationChannel(
        context: Context,
        importance: Int,
        showBadge: Boolean,
        name: String,
        description: String
    ) {

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channelId = "goals"
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.setShowBadge(showBadge)

            // Register the channel with the system
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }


    fun showNotification(context: Context?, intent: Intent?) {


        val name = intent?.getStringExtra("ReminderName")

        var builder = NotificationCompat.Builder(context!!, "goals")

            .setSmallIcon(R.drawable.button_bordered)
            .setContentText(context.resources.getString(R.string.reminder_text) + " " + name)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1001, builder.build())
    }


    fun deleteNotification(context: Context, id: Int) {
        val alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            intent.putExtra("goal", 1)
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmMgr.cancel(alarmIntent)

    }

}