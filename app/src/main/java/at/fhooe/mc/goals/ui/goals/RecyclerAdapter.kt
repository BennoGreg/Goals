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
import kotlinx.android.synthetic.main.nav_header_main.view.*


class RecyclerAdapter(private val data: List<Goal>, val clickListener: (Goal, Int) -> Boolean) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position],clickListener,position)
        val bool = data[position].buildQuit
        val progress = data[position].progress
        val frequency = data[position].goalFrequency

        if(progress!=null && frequency!=null){
            //val progInPercentage = (progress.toDouble()/frequency.toDouble()) * 100
            holder.progBar.max = frequency
            holder.progBar.setProgress(progress)

        }
        bool?.let {
            if (!bool){
                if (progress == data[position].goalFrequency){
                    holder.progBar.progressDrawable = holder.drawableDecFull
                }else {
                    holder.progBar.progressDrawable = holder.drawAbleDec
                }
            }else{
                if (progress == data[position].goalFrequency){
                    holder.progBar.progressDrawable = holder.drawableIncFull
                }else {
                    holder.progBar.progressDrawable = holder.drawAble
                }

            }
        }



    }
}