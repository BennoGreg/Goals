package at.fhooe.mc.goals.ui.newGoal.Reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import at.fhooe.mc.goals.R

class AlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {

        NotificationHelper.showNotification(context, intent)

    }
}