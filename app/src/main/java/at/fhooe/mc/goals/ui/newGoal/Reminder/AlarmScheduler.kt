package at.fhooe.mc.goals.ui.newGoal.Reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.text.format.DateUtils
import at.fhooe.mc.goals.Database.Reminder
import java.util.*
import java.util.Calendar.*

object AlarmScheduler {

    lateinit var alarmIntent: PendingIntent

    private fun createPendingIntent(context: Context, reminderData: ReminderData, day: String?): PendingIntent? {

       /* // create the intent using a unique type
        val intent = Intent(context.applicationContext, AlarmReceiver::class.java).apply {
            action = context.getString(R.string.action_notify_administer_medication)
            type = "$day-${reminderData.name}-${reminderData.medicine}-${reminderData.type.name}"
            putExtra(ReminderDialog.KEY_ID, reminderData.id)
        }

        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        */
        return null

    }


    fun cancelReminder(context: Context, id: Int){

        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        alarmMgr.cancel(alarmIntent)


    }

    fun scheduleAlarmsForReminder(context: Context, reminder: Reminder, id: Int, reminderPeriod: Int, name: String) {

        // get the AlarmManager reference
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            intent.putExtra("ReminderName",name)
            PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }



        var hour = reminder.hour
        if (reminder.am_pm == "PM"){
            hour += 12
        }

        // Set up the time to schedule the alarm
        val datetimeToAlarm = Calendar.getInstance(Locale.getDefault())
        datetimeToAlarm.timeInMillis = System.currentTimeMillis()
        datetimeToAlarm.set(HOUR_OF_DAY, hour)
        datetimeToAlarm.set(MINUTE, reminder.minute)
        datetimeToAlarm.set(SECOND, 0)
        datetimeToAlarm.set(MILLISECOND, 0)
        datetimeToAlarm.set(DAY_OF_MONTH, reminder.reminderDay)
        datetimeToAlarm.set(MONTH, reminder.reminderMonth-1)
        datetimeToAlarm.set(YEAR, reminder.reminderYear)


        when(reminderPeriod){
            0-> {
                alarmMgr.setExact(AlarmManager.RTC_WAKEUP,datetimeToAlarm.timeInMillis, alarmIntent)
            }
            1-> {
                alarmMgr.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    datetimeToAlarm.timeInMillis,
                    1000 * 60 * 60 * 24,
                    alarmIntent
                )
            }
            2-> {
                alarmMgr.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    datetimeToAlarm.timeInMillis,
                    1000 * 60 * 60 * 24 * 7,
                    alarmIntent
                )
            }
            3-> {

                alarmMgr.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    datetimeToAlarm.timeInMillis,
                    DateUtils.WEEK_IN_MILLIS*4,
                    alarmIntent
                )
            }
            4->{
                alarmMgr.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    datetimeToAlarm.timeInMillis,
                    DateUtils.YEAR_IN_MILLIS,
                    alarmIntent
                )
            }

        }



        //alarmMgr.cancel(alarmIntent)

    }


    /**
     * Schedules a single alarm
     */
    private fun scheduleAlarm(reminderData: ReminderData, dayOfWeek: Int, alarmIntent: PendingIntent?, alarmMgr: AlarmManager) {

     /*   // Set up the time to schedule the alarm
        val datetimeToAlarm = Calendar.getInstance(Locale.getDefault())
        datetimeToAlarm.timeInMillis = System.currentTimeMillis()
        datetimeToAlarm.set(Calendar.HOUR_OF_DAY, reminderData.hour)
        datetimeToAlarm.set(Calendar.MINUTE, reminderData.minute)
        datetimeToAlarm.set(Calendar.SECOND, 0)
        datetimeToAlarm.set(Calendar.MILLISECOND, 0)
        datetimeToAlarm.set(Calendar.DAY_OF_WEEK, dayOfWeek)

        // Compare the datetimeToAlarm to today
        val today = Calendar.getInstance(Locale.getDefault())
        if (shouldNotifyToday(dayOfWeek, today, datetimeToAlarm)) {

            // schedule for today
            alarmMgr.setRepeating(
                AlarmManager.RTC_WAKEUP,
                datetimeToAlarm.timeInMillis, (1000 * 60 * 60 * 24 * 7).toLong(), alarmIntent)
            return
        }

        // schedule 1 week out from the day
        datetimeToAlarm.roll(Calendar.WEEK_OF_YEAR, 1)
        alarmMgr.setRepeating(
            AlarmManager.RTC_WAKEUP,
            datetimeToAlarm.timeInMillis, (1000 * 60 * 60 * 24 * 7).toLong(), alarmIntent)*/
    }


    private fun shouldNotifyToday(dayOfWeek: Int, today: Calendar, datetimeToAlarm: Calendar): Boolean {
        return dayOfWeek == today.get(Calendar.DAY_OF_WEEK) &&
                today.get(Calendar.HOUR_OF_DAY) <= datetimeToAlarm.get(Calendar.HOUR_OF_DAY) &&
                today.get(Calendar.MINUTE) <= datetimeToAlarm.get(Calendar.MINUTE)
    }
}