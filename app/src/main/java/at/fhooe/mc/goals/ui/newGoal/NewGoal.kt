package at.fhooe.mc.goals.ui.newGoal

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import at.fhooe.mc.goals.Database.Goal
import at.fhooe.mc.goals.Database.RecyclerReminderData
import at.fhooe.mc.goals.Database.Reminder
import at.fhooe.mc.goals.Database.StatisticData
import at.fhooe.mc.goals.R
import at.fhooe.mc.goals.StatisticsSingleton
import at.fhooe.mc.goals.ui.goals.RecyclerAdapter
import at.fhooe.mc.goals.ui.goals.SwipeToDeleteCallback
import at.fhooe.mc.goals.ui.newGoal.Reminder.*
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_new_goal.*
import kotlinx.android.synthetic.main.content_new_goal.*
import kotlinx.android.synthetic.main.fragment_goals.*
import java.util.*
import kotlin.collections.ArrayList


class NewGoal : AppCompatActivity() {

    private lateinit var realm: Realm
    private var build = true
    private var currentPeriod = 0
    private var orange = "#e88317"
    lateinit var orangeGradient: Drawable
    private var green = "#4d9446"
    lateinit var greenGradient: Drawable

    private lateinit var reminderAdapter: ReminderRecyclerAdapter
    private var reminders = ArrayList<Reminder>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)







        /**
         * Register Notification channel
         */
        NotificationHelper.createNotificationChannel(this,
            NotificationManagerCompat.IMPORTANCE_HIGH, true,
            getString(R.string.app_name), "App notification channel.")



        setContentView(R.layout.activity_new_goal)

        initReminderRecyclerView() // set up reminder recycler view
        //addReminderDataSet() // add reminder dummy data
       /* linearLayoutManager = LinearLayoutManager(this)
        reminder_recycler.layoutManager = linearLayoutManager


*/
        greenGradient = getDrawable(R.drawable.green_button_gradient)!!
        orangeGradient = getDrawable(R.drawable.orange_button_gradient)!!

        setThemeGreen()
        updatePeriodColor(greenGradient)


        dailyButton.background = greenGradient
        realm = Realm.getDefaultInstance()


        setSupportActionBar(toolbar)


        saveButton.setOnClickListener{


            realm.executeTransaction{
                val goal = it.createObject(Goal::class.java)
                if (goalNameEditText.text.toString().isEmpty()){
                    goal.name = "My Goal"
                }else {
                    goal.name = goalNameEditText.text.toString()
                }
                goal.buildQuit = build
                goal.goalPeriod = currentPeriod

                if (frequencyEditText.text.toString().isEmpty()) goal.goalFrequency = 1
                else goal.goalFrequency =  Integer.parseInt(frequencyEditText.text.toString())
                goal.progress = 0

                for(reminder in reminders){
                    val managedReminder = realm.copyToRealm(reminder)
                    goal.reminderList?.add(managedReminder)

                    AlarmScheduler.scheduleAlarmsForReminder(this,reminder,reminder.remID.toInt(),reminder.reminderPeriod, goalNameEditText.text.toString())
                }
            }


            /*realm.executeTransactionAsync({

                val goal = it.createObject(Goal::class.java)
                if (goalNameEditText.text.toString().isEmpty()){
                    goal.name = "My Goal"
                }else {
                    goal.name = goalNameEditText.text.toString()
                }
                goal.buildQuit = build
                goal.goalPeriod = currentPeriod

                if (frequencyEditText.text.toString().isEmpty()) goal.goalFrequency = 1
                else goal.goalFrequency =  Integer.parseInt(frequencyEditText.text.toString())
                goal.progress = 0

                for(reminder in reminders){
                    val managedReminder = realm.copyToRealm(reminder)
                    goal.reminderList?.add(managedReminder)
                }


            },{
                Log.d("Goal", "Saved successfully")
            },{
                Log.d("Goal", "Not saved")
            })*/

            realm.beginTransaction()
            StatisticsSingleton.stats = realm.where(StatisticData::class.java).findFirst()
            if(StatisticsSingleton.stats==null){
                val stat = StatisticData()
                StatisticsSingleton.stats = realm.copyToRealm(stat)
            }
            StatisticsSingleton.updateNrOfGoals(currentPeriod,1)
            realm.commitTransaction()

            RecyclerReminderData.reminderList.clear()
            finish()
        }

        val swipeHandler = object : SwipeToDeleteCallback(this,false) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = reminder_recycler.adapter as ReminderRecyclerAdapter
                AlarmScheduler.cancelReminder(this@NewGoal,reminders[viewHolder.adapterPosition].remID.toInt())
                reminders.removeAt(viewHolder.adapterPosition)
                reminderAdapter.submitList(reminders)
                reminderAdapter.notifyDataSetChanged()
                adapter.notifyItemRemoved(viewHolder.adapterPosition)


            }

        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(reminder_recycler)


        content_new_goal.setOnTouchListener { v, event ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken,0)
            false
        }



        buildButton.setOnClickListener {

            setThemeGreen() // change Theme of the screen
            build = true
            updatePeriodColor(greenGradient)


        }



        quitButton.setOnClickListener {

            setThemeOrange()
            build = false
            updatePeriodColor(orangeGradient)

        }

        dailyButton.setOnClickListener {

            currentPeriod = 0
            if (build) updatePeriodColor(greenGradient) else updatePeriodColor(orangeGradient)

        }
        weeklyButton.setOnClickListener {

            currentPeriod = 1
            if (build) updatePeriodColor(greenGradient) else updatePeriodColor(orangeGradient)
        }
        monthlyButton.setOnClickListener {

            currentPeriod = 2
            if (build) updatePeriodColor(greenGradient) else updatePeriodColor(orangeGradient)
        }

        yearlyButton.setOnClickListener {

            NotificationHelper.delteNotification(this,1002)

            currentPeriod = 3
            if (build) updatePeriodColor(greenGradient) else updatePeriodColor(orangeGradient)
        }

        addReminderButton.setOnClickListener {


            val i = Intent(this, NewReminder::class.java)
            startActivityForResult(i,1)

        }



    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1) {

           // Toast.makeText(this, "Day: " + ReminderData.reminderDay + "Month: " + ReminderData.reminderMonth + "Year: " + ReminderData.reminderYear, Toast.LENGTH_SHORT).show()
           // Toast.makeText(this, "Time: " + ReminderData.minute + ":"  + ReminderData.hour + " " + ReminderData.am_pm, Toast.LENGTH_SHORT).show()
            var day = ReminderData.reminderDay.toString()
            if (ReminderData.reminderDay < 10) day = "0$day"

            var month = ReminderData.reminderMonth.toString()
            if(ReminderData.reminderMonth < 10) month = "0$month"

            var hour = ReminderData.hour.toString()
            if(ReminderData.hour < 10) hour = "0$hour"

            var minute = ReminderData.minute.toString()
            if(ReminderData.minute < 10) minute = "0$minute"

            var date = day+ "." + month + "."+ ReminderData.reminderYear.toString() + " - " + hour + ":" + minute + " " + ReminderData.am_pm
            var period = "never"
            when (ReminderData.reminderPeriod) {

                0 -> {

                    period = "never"
                }
                1 -> {

                    period = "Daily"
                }
                2 -> {

                    period = "Weekly"
                }
                3 -> {

                    period = "Monthly"
                }
                4 -> {

                    period = "Yearly"
                }
            }
            RecyclerReminderData.addReminder(date, period)


            val id = UUID.randomUUID().leastSignificantBits.toInt()

            val reminder= Reminder(id.toLong(),ReminderData.reminderDay,ReminderData.reminderMonth,ReminderData.reminderYear,ReminderData.hour,ReminderData.minute,ReminderData.am_pm, ReminderData.reminderPeriod)
            reminders.add(reminder)

            reminderAdapter.submitList(reminders)

            reminderAdapter.notifyDataSetChanged()




        }
    }



    /**
     * Sets up the recyclerView for the Reminders
     */
    private fun initReminderRecyclerView(){


        reminder_recycler.apply {
           layoutManager = LinearLayoutManager(this@NewGoal)
            reminderAdapter = ReminderRecyclerAdapter()
            reminder_recycler.adapter = reminderAdapter
        }
    }

    /**
     * Adds the dummy reminders to the data set
     */
    private fun addReminderDataSet(){
        val reminderData = RecyclerReminderData.reminderList
        reminderAdapter.submitList(reminderData)
    }








    /**
     * Function to update the current Color of the goal period buttons.
     */
    fun updatePeriodColor(gradient: Drawable){

        when (currentPeriod){

            0->{
                dailyButton.background = gradient
                weeklyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                monthlyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                yearlyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                frequencyTextField.text = resources.getString(R.string.frequency_textfield_daily)

            }
            1->{
                dailyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                weeklyButton.background = gradient
                monthlyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                yearlyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                frequencyTextField.text = resources.getString(R.string.frequency_textfield_weekly)
            }
            2->{
                dailyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                weeklyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                monthlyButton.background = gradient
                yearlyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                frequencyTextField.text = resources.getString(R.string.frequency_textfield_monthly)
            }
            3->{
                dailyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                weeklyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                monthlyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                yearlyButton.background = gradient
                frequencyTextField.text = resources.getString(R.string.frequency_textfield_yearly)
            }

        }

    }







    /**
     * Function to set the theme of the NewGoalScreen to Green (Build-Goal)
     */
    fun setThemeGreen(){

        window.statusBarColor = ContextCompat.getColor(this,android.R.color.black)

        toolbar_layout.setContentScrimResource(R.drawable.green_button_gradient)
        toolbar.setBackgroundResource(R.drawable.green_button_gradient)
        app_bar.setBackgroundResource(R.drawable.green_button_gradient)
        toolbar_layout.setBackgroundResource(R.drawable.green_button_gradient)
        goalNameTextView.setTextColor(Color.parseColor(green))
        buildButton.setBackgroundResource(R.drawable.green_button_gradient)
        quitButton.setBackgroundColor(Color.parseColor("lightgray"))
        goalPeriodTextView.setTextColor(Color.parseColor(green))
        goalTypeView.setTextColor(Color.parseColor(green))
        achieveTextField.setTextColor(Color.parseColor(green))
        reminderTextfield.setTextColor(Color.parseColor(green))

    }

    /**
     * Function to set the theme of the NewGoalScreen to Green (Build-Goal)
     */
    fun setThemeOrange(){
        toolbar_layout.setContentScrimResource(R.drawable.orange_button_gradient)
        toolbar.setBackgroundResource(R.drawable.orange_button_gradient)
        app_bar.setBackgroundResource(R.drawable.orange_button_gradient)
        toolbar_layout.setBackgroundResource(R.drawable.orange_button_gradient)
        goalNameTextView.setTextColor(Color.parseColor(orange))
        quitButton.setBackgroundResource(R.drawable.orange_button_gradient)
        buildButton.setBackgroundColor(Color.parseColor("lightgray"))
        goalTypeView.setTextColor(Color.parseColor(orange))
        achieveTextField.setTextColor(Color.parseColor(orange))
        goalPeriodTextView.setTextColor(Color.parseColor(orange))
        reminderTextfield.setTextColor(Color.parseColor(orange))

    }




}

