package at.fhooe.mc.goals.ui.newGoal.Reminder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import at.fhooe.mc.goals.Database.Reminder
import at.fhooe.mc.goals.R

class ReminderRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var reminders: List<Reminder> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ReminderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recyclerview_item_row,
                parent,
                false
            )
        )

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ReminderViewHolder -> {

                var day = reminders[position].reminderDay.toString()
                if (reminders[position].reminderDay < 10) day = "0$day"

                var month = reminders[position].reminderMonth.toString()
                if (reminders[position].reminderMonth < 10) month = "0$month"

                var hour = reminders[position].hour.toString()
                if (reminders[position].hour < 10) hour = "0$hour"

                var minute = reminders[position].minute.toString()
                if (reminders[position].minute < 10) minute = "0$minute"

                var date =
                    day + "." + month + "." + reminders[position].reminderYear + " - " + hour + ":" + minute + " " + reminders[position].am_pm
                holder.reminderName.setText(date)
                var period = "never"
                when (reminders[position].reminderPeriod) {

                    0 -> {

                        period = "Never"
                    }
                    1 -> {

                        period = "Daily"
                    }
                    2 -> {

                        period = "Weekly"
                    }
                    3 -> {

                        period = "Monthly"
                    }
                    4 -> {

                        period = "Yearly"
                    }
                }
                holder.reminderDate.setText(period)
            }
        }
    }

    override fun getItemCount(): Int {
        return reminders.size
    }

    fun submitList(reminderList: List<Reminder>) {
        reminders = reminderList
    }


}