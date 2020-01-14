package at.fhooe.mc.goals.ui.goals

import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import at.fhooe.mc.goals.Database.Goal
import at.fhooe.mc.goals.R

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    var progBar: ProgressBar = itemView.findViewById(R.id.progressBar)
    var drawAble: Drawable =  itemView.resources.getDrawable(R.drawable.rounded_corners_progressbar_increase,null)
    var drawAbleDec: Drawable =  itemView.resources.getDrawable(R.drawable.rounded_corners_progressbar_decrease,null)
    var inProgressTv: TextView = itemView.findViewById(R.id.tv_progressBar)
    var drawableIncFull: Drawable = itemView.resources.getDrawable(R.drawable.rounded_corners_progressbar_inc_full,null)
    var drawableDecFull: Drawable = itemView.resources.getDrawable(R.drawable.rounded_corners_progressbar_dec_full,null)

    fun bind(goal: Goal, clickListener: (Goal, Int) -> Boolean, position: Int){



        itemView.setOnTouchListener(object : View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                return false
            }

        })
        val progress = goal.progress
        val name = goal.name
        val frequency = goal.goalFrequency
        val period = goal.goalPeriod
        val periodString = itemView.resources.getStringArray(R.array.period)
        if(period != null){
            inProgressTv.setText(itemView.resources.getString(R.string.goalNameProgress,name,progress,frequency,periodString[period]))
        }



    }



}