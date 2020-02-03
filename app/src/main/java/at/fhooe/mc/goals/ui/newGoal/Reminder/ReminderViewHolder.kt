package at.fhooe.mc.goals.ui.newGoal.Reminder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import at.fhooe.mc.goals.Database.Reminder
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*

class ReminderViewHolder constructor(
    itemView: View
): RecyclerView.ViewHolder(itemView) {

    var reminderName = itemView.reminderDate
    var reminderDate = itemView.reminderPeriod

    fun bind(reminder: Reminder){

        reminderName.setText("f")
        reminderDate.setText("h")
    }
}