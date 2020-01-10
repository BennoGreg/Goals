package at.fhooe.mc.goals.ui.goals

import android.graphics.Color
import android.graphics.ColorFilter
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import at.fhooe.mc.goals.Database.Goal
import at.fhooe.mc.goals.R
import android.graphics.PorterDuff
import android.widget.ProgressBar




class RecyclerAdapter(private val data: List<Goal>, val clickListener: (Goal, Int) -> Boolean) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //holder.tv.text = data[position].name
        holder.bind(data[position],clickListener,position)
        val bool = data[position].buildQuit
        val progress = data[position].progress
        if(progress!=null){
            holder.progBar.setProgress(progress)
        }
        bool?.let {
            if (!bool){
                holder.progBar.progressDrawable = holder.drawAbleDec
                holder.progBar.scaleX = -1f
            }
        }
        /*if (bool != null && progress!=null) {
            if (bool) {
                holder.progBar.setProgress(progress)


            } else {
                holder.progBar.setProgress(progress)
                holder.progBar.progressDrawable = holder.drawAbleDec
                holder.progBar.scaleX = -1f
            }
        }*/


    }
}