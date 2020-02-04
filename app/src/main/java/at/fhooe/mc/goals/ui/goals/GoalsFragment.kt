package at.fhooe.mc.goals.ui.goals

import android.app.AlertDialog
import android.content.Intent
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
import androidx.recyclerview.widget.ItemTouchHelper
import at.fhooe.mc.goals.Database.StatisticData
import at.fhooe.mc.goals.MainActivity
import at.fhooe.mc.goals.StatisticsSingleton
import at.fhooe.mc.goals.ui.editGoal.EditGoal
import at.fhooe.mc.goals.ui.editGoal.GoalSingleton
import at.fhooe.mc.goals.ui.newGoal.Reminder.AlarmScheduler
import io.realm.Realm


class GoalsFragment : Fragment() {

    private lateinit var goalsViewModel: GoalsViewModel
    private lateinit var realm: Realm

    private var statistics: StatisticData? = null


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

        val activity = activity as MainActivity
        recyclerView.layoutManager = LinearLayoutManager(activity)


        activity.fab.show()

        //startWork()
        realm.beginTransaction()

        val result = realm.where(Goal::class.java).findAll()

        GoalSingleton.goalList = result

        /*for(goalList in result){
            StatisticsSingleton.updateNrOfGoals(goalList.goalPeriod!!,1)
        }*/

        /*statistics = realm.where(StatisticData::class.java).findFirst()

        if(statistics == null){
            statistics = StatisticData()
            val managedStats = realm.copyToRealm(statistics)

        }*/



        /*if (statistics != null){
            for (goalList in result){
                activity.updateStatistic(goalList)
            }
        }*/

        data.clear()

        realm.commitTransaction()




        if (result != null){
            data.addAll(realm.copyFromRealm(result))
        }


        recyclerView.adapter = RecyclerAdapter(data) { goal: Goal, position: Int -> goalClicked(goal, position) }

        val swipeHandler = object : SwipeToDeleteCallback(context!!,true) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recyclerView.adapter as RecyclerAdapter
                if (direction == ItemTouchHelper.LEFT){

                    val dialog = AlertDialog.Builder(this@GoalsFragment.context).setMessage(R.string.askDelete).setPositiveButton(R.string.yes_delete){
                            _, _ ->
                        run {

                            val period = data[viewHolder.adapterPosition].goalPeriod as Int
                            val reminders = data[viewHolder.adapterPosition].reminderList
                            if(reminders != null){
                                for (reminder in reminders){
                                    AlarmScheduler.cancelReminder(context!!,reminder.remID.toInt()

                                    )
                                }
                            }


                            data.removeAt(viewHolder.adapterPosition)
                            realm.beginTransaction()
                            result.deleteFromRealm(viewHolder.adapterPosition)
                            StatisticsSingleton.updateNrOfGoals(period, -1)

                            realm.commitTransaction()

                            adapter.notifyDataSetChanged()
                        }
                    }.setNegativeButton(R.string.no_delete){
                            _, _ -> adapter.notifyItemChanged(viewHolder.adapterPosition)
                    }.setOnCancelListener { adapter.notifyItemChanged(viewHolder.adapterPosition) }.create()

                    dialog.setCanceledOnTouchOutside(true)
                    dialog.show()

                }else if(direction == ItemTouchHelper.RIGHT){

                    val position = viewHolder.adapterPosition
                    //realm.beginTransaction()
                    var currentProg = data[position].progress

                    if (currentProg == data[position].goalFrequency){
                        val dialogFull = AlertDialog.Builder(this@GoalsFragment.context).setMessage(resources.getString(R.string.goal_achieved)).setNeutralButton("OK"){
                            _,_ -> adapter.notifyItemChanged(viewHolder.adapterPosition)
                        }
                            .setOnCancelListener{ adapter.notifyItemChanged(viewHolder.adapterPosition) }
                            .create()

                        dialogFull.setCanceledOnTouchOutside(true)
                        dialogFull.show()
                    }

                    else{
                        val dialog = AlertDialog.Builder(this@GoalsFragment.context).setMessage(R.string.askConfirm).setPositiveButton(R.string.yesConfirm){
                                _, _ ->
                            run {


                                if (currentProg != null && currentProg != data[position].goalFrequency) {
                                    currentProg += 1
                                    realm.executeTransactionAsync({ realm ->
                                        val res = realm.where(Goal::class.java).findAll()
                                        res[position]?.progress = currentProg
                                        data[position].progress = currentProg

                                    }, {
                                        Log.i("My","Saving was successful")
                                    }, {
                                        Log.e("My","Saving was not successful")
                                    })

                                    if (currentProg == data[position].goalFrequency){
                                        val goal = data[position]
                                        val period = goal.goalPeriod
                                        if(period !=null){
                                            realm.beginTransaction()
                                            StatisticsSingleton.updateAchieved(period,1)
                                            realm.commitTransaction()
                                        }

                                    }

                                }
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
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun goalClicked(goal: Goal, position: Int) : Boolean{

        realm.beginTransaction()
        val result = realm.where(Goal::class.java).findAll()

        realm.commitTransaction()
        val intent = Intent(context, EditGoal::class.java)
        intent.putExtra("position", position)

        startActivityForResult(intent,1)

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1){
            activity?.finish()

            startActivity(activity?.intent?.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        }
    }




}