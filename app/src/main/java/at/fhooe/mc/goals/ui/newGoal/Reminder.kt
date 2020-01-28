package at.fhooe.mc.goals.ui.newGoal

data class Reminder(
    var name: String,
    var time: String
){

}


class dummyData{

    companion object{
        fun createDummyData(): ArrayList<Reminder>{

            val list = ArrayList<Reminder>()
            list.add(Reminder("ReminderEins", "12:00"))
            list.add(Reminder("ReminderZwei", "Montag, 19:00"))
            list.add(Reminder("ReminderDrei", "13:20"))
            list.add(Reminder("ReminderVier", "Dienstag, 19:39"))

            return list
        }

    }
}