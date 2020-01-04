package at.fhooe.mc.goals.ui.goals

import android.graphics.Color
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import at.fhooe.mc.goals.R

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){

    val tv: TextView = view.findViewById(R.id.goalTextView)
    var progBar: ProgressBar = view.findViewById(R.id.progressBar)



}