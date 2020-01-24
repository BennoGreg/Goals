package at.fhooe.mc.goals.ui.goals

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import at.fhooe.mc.goals.Database.Goal
import at.fhooe.mc.goals.R
import kotlinx.android.synthetic.main.fragment_goals.*
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import at.fhooe.mc.goals.Database.StatisticData
import io.realm.Realm




class GoalsFragment : Fragment() {

    private lateinit var goalsViewModel: GoalsViewModel
    private lateinit var realm: Realm

    val data = ArrayList<Goal>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        goalsViewModel =
            ViewModelProviders.of(this).get(GoalsViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_goals, container, false)

        realm = Realm.getDefaultInstance()

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val activity = activity
        recyclerView.layoutManager = LinearLayoutManager(activity)




        //startWork()
        realm.beginTransaction()

        val result = realm.where(Goal::class.java).findAll()

        data.clear()

        realm.commitTransaction()
        if (result != null){
            data.addAll(realm.copyFromRealm(result))
        }


        recyclerView.adapter = RecyclerAdapter(data) { goal: Goal, position: Int -> goalClicked(goal, position) }

        val swipeHandler = object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recyclerView.adapter as RecyclerAdapter
                if (direction == ItemTouchHelper.LEFT){

                    val dialog = AlertDialog.Builder(this@GoalsFragment.context).setMessage(R.string.askDelete).setPositiveButton(R.string.yes_delete){
                            _, _ ->
                        run {


                            data.removeAt(viewHolder.adapterPosition)
                            realm.beginTransaction()
                            result.deleteFromRealm(viewHolder.adapterPosition)
                            realm.commitTransaction()

                            adapter.notifyItemRemoved(viewHolder.adapterPosition)
                        }
                    }.setNegativeButton(R.string.no_delete){
                            _, _ -> adapter.notifyItemChanged(viewHolder.adapterPosition)
                    }.setOnCancelListener { adapter.notifyItemChanged(viewHolder.adapterPosition) }.create()

                    dialog.setCanceledOnTouchOutside(true)
                    dialog.show()

                }else if(direction == ItemTouchHelper.RIGHT){

                    

                    val dialog = AlertDialog.Builder(this@GoalsFragment.context).setMessage(R.string.askConfirm).setPositiveButton(R.string.yesConfirm){
                        _, _ ->
                        run {
                            val position = viewHolder.adapterPosition
                            //realm.beginTransaction()
                            val currentProg = data[position].progress
                            if (currentProg != null && currentProg != data[position].goalFrequency) {
                                data[position].progress = currentProg + 1
                                realm.executeTransactionAsync({ realm ->
                                    val res = realm.where(Goal::class.java).findAll()
                                    res[position]?.progress = data[position].progress
                                }, {
                                    Log.i("My","Saving was successful")
                                }, {
                                    Log.e("My","Saving was not successful")
                                })


                            }

                            if (data[position].progress == data[position].goalFrequency){


                            }

                            //result[position]?.progress = data[position].progress
                            //realm.commitTransaction()
                            adapter.notifyItemChanged(position)
                        }
                    }.setNegativeButton(R.string.noConfirm){
                        _, _ -> adapter.notifyItemChanged(viewHolder.adapterPosition)
                    }.setOnCancelListener { adapter.notifyItemChanged(viewHolder.adapterPosition) }.create()

                    dialog.setCanceledOnTouchOutside(true)
                    dialog.show()

                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun goalClicked(goal: Goal, position: Int) : Boolean{

        realm.beginTransaction()
        data[position].progress = data[position].progress!! + 1
        realm.commitTransaction()

        Log.i("MyTag", "Progress on position $position is ${data[position].progress}")
        recyclerView.adapter?.notifyItemChanged(position)
        Toast.makeText(activity,"Clicked: ${goal.name} at position $position", Toast.LENGTH_SHORT).show()
        return true
    }

    private fun updateStatistic(goal: Goal){

        realm.beginTransaction()

        var statistic = StatisticData(sID = 0)


        when(goal.goalPeriod){

            
        }

        realm.commitTransaction()
    }

}