package at.fhooe.mc.goals.ui.editGoal

import at.fhooe.mc.goals.Database.Goal

object GoalSingleton {

    var goalList: List<Goal>? = null

    fun getGoal(position: Int): Goal {
        val list = goalList as List<Goal>

        return list[position]
    }


}