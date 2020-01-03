package at.fhooe.mc.goals.ui.goals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import at.fhooe.mc.goals.Database.Goal

class GoalsViewModel : ViewModel() {

    private val data = MutableLiveData<ArrayList<Goal>>().apply {
        val goal = Goal("Quit smoking", true,40,2)
        val list = ArrayList<Goal>()
        list.add(goal)
        value = list
    }
    val goalList: LiveData<ArrayList<Goal>> = data

}