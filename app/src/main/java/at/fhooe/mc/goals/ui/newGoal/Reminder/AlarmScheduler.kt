package at.fhooe.mc.goals.ui.newGoal.Reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.text.format.DateUtils
import at.fhooe.mc.goals.R
import java.util.*

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

    fun scheduleAlarmsForReminder(context: Context, reminderData: ReminderData, id: Long, reminderPeriod: Int) {

        // get the AlarmManager reference
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            intent.putExtra("ID", id)
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }




        // Set up the time to schedule the alarm
        val datetimeToAlarm = Calendar.getInstance(Locale.getDefault())
        datetimeToAlarm.timeInMillis = System.currentTimeMillis()
        datetimeToAlarm.set(Calendar.HOUR_OF_DAY, reminderData.hour)
        datetimeToAlarm.set(Calendar.MINUTE, reminderData.minute)
        datetimeToAlarm.set(Calendar.SECOND, 0)
        datetimeToAlarm.set(Calendar.MILLISECOND, 0)
        datetimeToAlarm.set(Calendar.DAY_OF_MONTH, ReminderData.reminderMonth)
        datetimeToAlarm.set(Calendar.DAY_OF_YEAR, ReminderData.reminderDay)
        datetimeToAlarm.set(Calendar.YEAR, ReminderData.reminderYear)

        when(reminderPeriod){
            0-> {
                alarmMgr?.set(AlarmManager.RTC_WAKEUP,datetimeToAlarm.timeInMillis, alarmIntent)
            }
            1-> {
                alarmMgr?.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    datetimeToAlarm.timeInMillis,
                    1000 * 60 * 60 * 24,
                    alarmIntent
                )
            }
            2-> {
                alarmMgr?.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    datetimeToAlarm.timeInMillis,
                    1000 * 60 * 60 * 24 * 7,
                    alarmIntent
                )
            }
            3-> {

                alarmMgr?.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    datetimeToAlarm.timeInMillis,
                    DateUtils.WEEK_IN_MILLIS*4,
                    alarmIntent
                )
            }
            4->{
                alarmMgr?.setInexactRepeating(
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