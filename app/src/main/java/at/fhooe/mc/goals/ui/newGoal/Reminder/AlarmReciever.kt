package at.fhooe.mc.goals.ui.newGoal.Reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import at.fhooe.mc.goals.R

class AlarmReceiver : BroadcastReceiver() {

    private val TAG = AlarmReceiver::class.java.simpleName

    override fun onReceive(context: Context?, intent: Intent?) {


        //Log.i("Test","TEST")

        NotificationHelper.demoNoti(context!!, intent?.getStringExtra("Goal"))

    }
    }