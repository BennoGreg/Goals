package at.fhooe.mc.goals

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import at.fhooe.mc.goals.Database.Goal
import io.realm.Realm
import java.util.*

class UpdateDataBase : BroadcastReceiver(){
    lateinit var realm: Realm
    override fun onReceive(context: Context?, intent: Intent?) {

        val intent = Intent(context, UpdateDataBase::class.java)
        intent.putExtra("activate",true)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY,0)
        calendar.set(Calendar.MINUTE,0)
        calendar.set(Calendar.SECOND,0)

        val am = getSystemService(context!!,AlarmManager::class.java) as AlarmManager
        am.setExact(AlarmManager.RTC,calendar.timeInMillis,pendingIntent)


        realm = Realm.getDefaultInstance()

        realm.beginTransaction()
        val results = realm.where(Goal::class.java).findAll()
        if(results != null){
            val list = ArrayList<Goal>()
            list.addAll(results)
        }


        for(goal in results){
            checkGoal(goal)
        }

        realm.commitTransaction()



    }

    private fun checkGoal(goal: Goal){

        var period = goal.goalPeriod as Int

        val calendar = Calendar.getInstance()

        val weekDay = calendar.get(Calendar.DAY_OF_WEEK)

        val monthDay = calendar.get(Calendar.DAY_OF_MONTH)

        val yearDay = calendar.get(Calendar.DAY_OF_YEAR)

        when(period){

            0 -> goal.progress = 0

            1 -> {
                if (weekDay == 1) goal.progress = 0
            }
            2 -> {
                if (monthDay == 1) goal.progress = 0
            }
            3 -> if (yearDay == 1) goal.progress = 0
        }

    }


}