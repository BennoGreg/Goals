package at.fhooe.mc.goals.ui.goals

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView
import at.fhooe.mc.goals.R

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){


    //val tv: TextView = view.findViewById(R.id.goalTextView)
    var progBar: ProgressBar = view.findViewById(R.id.progressBar)
    var drawAble: Drawable =  view.resources.getDrawable(R.drawable.rounded_corners_progressbar_increase,null)
    var drawAbleDec: Drawable =  view.resources.getDrawable(R.drawable.rounded_corners_progressbar_decrease,null)


}