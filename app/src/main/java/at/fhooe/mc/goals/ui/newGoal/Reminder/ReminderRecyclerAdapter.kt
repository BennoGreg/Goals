package at.fhooe.mc.goals.ui.newGoal.Reminder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import at.fhooe.mc.goals.R

class ReminderRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


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
        when(holder){
            is ReminderViewHolder ->{
                holder.bind(reminders.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return reminders.size
    }

    fun submitList(reminderList: List<Reminder>){
        reminders = reminderList
    }


}