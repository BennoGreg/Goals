package at.fhooe.mc.goals.ui.goals

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import at.fhooe.mc.goals.R

abstract class SwipeToDeleteCallback(context: Context, isGoal: Boolean) : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {


    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_deletebutton_white)
    private val plusButton = ContextCompat.getDrawable(context, R.drawable.ic_add_24px)
    private val intrinsicWidthDelete = deleteIcon?.intrinsicWidth as Int
    private val intrinsicHeightDelete = deleteIcon?.intrinsicHeight as Int
    private val intrinsicWidthPlus = plusButton?.intrinsicWidth as Int
    private val intrinsicHeightPlus = plusButton?.intrinsicHeight as Int
    private val background = ColorDrawable()
    private val backgroundColor = Color.parseColor("#f44336")
    private val backgroundSwipeRight = ContextCompat.getColor(context,R.color.colorPrimary)
    private val clearPaint = Paint().apply{xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)}
    private val swipeRight = isGoal

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }



    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top
        val isCanceled = dX == 0f && !isCurrentlyActive

        if (dX < 0){

            if (isCanceled) {
                clearCanvas(c, itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                return
            }


            background.color = backgroundColor
            background.setBounds(itemView.right + dX.toInt(),itemView.top,itemView.right,itemView.bottom)
            background.draw(c)

            val iconTop  = itemView.top + (itemHeight - intrinsicHeightDelete) / 2
            val iconMargin = (itemHeight - intrinsicHeightDelete) / 2
            val iconLeft = itemView.right - iconMargin - intrinsicWidthDelete
            val iconRight = itemView.right - iconMargin
            val iconBottom = iconTop + intrinsicWidthDelete

            deleteIcon?.setBounds(iconLeft,iconTop,iconRight,iconBottom)
            deleteIcon?.draw(c)
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }else if (dX > 0 && swipeRight){
            if (isCanceled){
                clearCanvas(c,itemView.left.toFloat(),itemView.top.toFloat(),itemView.left + dX/3, itemView.bottom.toFloat())
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                return
            }

            background.color = backgroundSwipeRight
            background.setBounds(itemView.left, itemView.top,itemView.left + dX.toInt()/4 ,itemView.bottom)
            background.draw(c)


            val iconTop = itemView.top + (itemHeight - intrinsicHeightPlus) / 2
            val iconMargin = (itemHeight - intrinsicHeightPlus) /2
            val iconLeft = itemView.left + iconMargin
            val iconRight = itemView.left + iconMargin + intrinsicWidthPlus
            val iconBottom = iconTop + intrinsicWidthPlus

            plusButton?.setBounds(iconLeft,iconTop,iconRight,iconBottom)
            plusButton?.draw(c)

            super.onChildDraw(c, recyclerView, viewHolder, dX/4, dY, actionState, isCurrentlyActive)
        }


    }

    private fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        c?.drawRect(left, top, right, bottom, clearPaint)
    }
}