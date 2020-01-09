package at.fhooe.mc.goals.ui.goals

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import at.fhooe.mc.goals.Database.Goal
import at.fhooe.mc.goals.R

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){


    //val tv: TextView = itemView.findViewById(R.id.goalTextView)
    var progBar: ProgressBar = itemView.findViewById(R.id.progressBar)
    var drawAble: Drawable =  itemView.resources.getDrawable(R.drawable.rounded_corners_progressbar_increase,null)
    var drawAbleDec: Drawable =  itemView.resources.getDrawable(R.drawable.rounded_corners_progressbar_decrease,null)

    fun bind(goal: Goal, clickListener: (Goal, Int) -> Unit, position: Int){
        itemView.setOnClickListener { clickListener(goal, position) }
    }


}