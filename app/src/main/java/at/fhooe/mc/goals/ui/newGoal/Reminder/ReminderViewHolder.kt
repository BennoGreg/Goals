package at.fhooe.mc.goals.ui.newGoal.Reminder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import at.fhooe.mc.goals.Database.Reminder
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*

class ReminderViewHolder constructor(
    itemView: View
): RecyclerView.ViewHolder(itemView) {

    val reminderName = itemView.reminderName
    val reminderDate = itemView.reminderDate

    fun bind(reminder: Reminder){

        reminderName.setText("f")
        reminderDate.setText("h")
    }
}