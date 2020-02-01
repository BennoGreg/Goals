package at.fhooe.mc.goals.Database

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*
import kotlin.collections.ArrayList

open class Reminder(

    @PrimaryKey
    var remID: Long = 0L,
    var reminderDay: Int = Calendar.DAY_OF_MONTH,
    var reminderMonth: Int = Calendar.MONTH,
    var reminderYear: Int = Calendar.YEAR,

    var hour: Int = Calendar.HOUR,
    var minute: Int = Calendar.MINUTE,
    var am_pm: String = "AM",

    var reminderPeriod: Int = 0

): RealmObject(){

}


class RecyclerReminderData{

    companion object{

        val reminderList = ArrayList<Reminder>()


        fun addReminder(name: String,time: String){


        }





    }
}