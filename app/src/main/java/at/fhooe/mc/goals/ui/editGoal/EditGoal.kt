package at.fhooe.mc.goals.ui.editGoal

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import at.fhooe.mc.goals.Database.RecyclerReminderData
import at.fhooe.mc.goals.Database.Reminder
import at.fhooe.mc.goals.R
import at.fhooe.mc.goals.ui.statistics.StatisticsSingleton
import at.fhooe.mc.goals.ui.goals.SwipeToDeleteCallback
import at.fhooe.mc.goals.ui.newGoal.Reminder.AlarmScheduler
import at.fhooe.mc.goals.ui.newGoal.Reminder.NewReminder
import at.fhooe.mc.goals.ui.newGoal.Reminder.ReminderData
import at.fhooe.mc.goals.ui.newGoal.Reminder.ReminderRecyclerAdapter

import io.realm.Realm
import io.realm.RealmList
import kotlinx.android.synthetic.main.activity_edit_goal.*
import kotlinx.android.synthetic.main.activity_edit_goal.app_bar
import kotlinx.android.synthetic.main.activity_edit_goal.toolbar
import kotlinx.android.synthetic.main.activity_edit_goal.toolbar_layout
import kotlinx.android.synthetic.main.content_edit_goal.achieveTextField
import kotlinx.android.synthetic.main.content_edit_goal.buildButton
import kotlinx.android.synthetic.main.content_edit_goal.dailyButton
import kotlinx.android.synthetic.main.content_edit_goal.frequencyEditText
import kotlinx.android.synthetic.main.content_edit_goal.frequencyTextField
import kotlinx.android.synthetic.main.content_edit_goal.goalNameEditText
import kotlinx.android.synthetic.main.content_edit_goal.goalNameTextView
import kotlinx.android.synthetic.main.content_edit_goal.goalPeriodTextView
import kotlinx.android.synthetic.main.content_edit_goal.goalTypeView
import kotlinx.android.synthetic.main.content_edit_goal.monthlyButton
import kotlinx.android.synthetic.main.content_edit_goal.quitButton
import kotlinx.android.synthetic.main.content_edit_goal.weeklyButton
import kotlinx.android.synthetic.main.content_edit_goal.yearlyButton
import kotlinx.android.synthetic.main.content_new_goal.*
import java.util.*


class EditGoal : AppCompatActivity() {

    lateinit var realm: Realm

    private var build = true
    private var currentPeriod = 0
    private var orange = "#e88317"
    lateinit var orangeGradient: Drawable
    private var green = "#4d9446"
    lateinit var greenGradient: Drawable
    lateinit var reminders: RealmList<Reminder>
    lateinit var reminderAdapter: ReminderRecyclerAdapter
    var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_goal)
        setSupportActionBar(toolbar)

        initReminderRecyclerView() // set up reminder recycler view
        position = intent.getIntExtra("position",0)

        val goal = GoalSingleton.getGoal(position)

        reminders = goal.reminderList!!

        currentPeriod = goal.goalPeriod!!
        val oldPeriod = currentPeriod


        initReminderRecyclerView()

        reminderAdapter.submitList(reminders)
        reminderAdapter.notifyDataSetChanged()

        greenGradient = getDrawable(R.drawable.green_button_gradient)!!
        orangeGradient = getDrawable(R.drawable.orange_button_gradient)!!

        updatePeriodColor(greenGradient)
        setThemeGreen()

        dailyButton.setBackgroundColor(Color.parseColor(green))
        realm = Realm.getDefaultInstance()

        goalNameEditText.setText(goal.name)

        val oldFrequency = goal.goalFrequency

        if (goal.buildQuit == false){
            setThemeOrange()
            build = false
            updatePeriodColor(orangeGradient)
        }else{
            setThemeGreen() // change Theme of the screen
            build = true
            updatePeriodColor(greenGradient)
        }


        frequencyEditText.setText(goal.goalFrequency.toString())



        fab.setOnClickListener {
            /*realm.executeTransactionAsync({

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



            },{
                Log.d("Goal", "Saved successfully")
            },{
                Log.d("Goal", "Not saved")
            })*/

            realm.beginTransaction()
            if (goalNameEditText.text.toString().isEmpty()){
                goal.name = "My Goal"
            }else {
                goal.name = goalNameEditText.text.toString()
            }
            goal.buildQuit = build
            goal.goalPeriod = currentPeriod

            if (frequencyEditText.text.toString().isEmpty()) goal.goalFrequency = 1
            else goal.goalFrequency =  Integer.parseInt(frequencyEditText.text.toString())

            val isAchieved = goal.progress == oldFrequency
            val isEqualFrequency = goal.goalFrequency == oldFrequency

            StatisticsSingleton.updateAfterEdit(oldPeriod,currentPeriod,isAchieved,isEqualFrequency)

            realm.commitTransaction()

            finish()
        }

        content_edit_goal.setOnTouchListener { v, event ->
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

            currentPeriod = 3
            if (build) updatePeriodColor(greenGradient) else updatePeriodColor(orangeGradient)
        }

        addReminderButton.setOnClickListener {

            val i = Intent(this, NewReminder::class.java)
            startActivityForResult(i,1)
        }

        val swipeHandler = object : SwipeToDeleteCallback(this,false) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = reminder_recycler.adapter as ReminderRecyclerAdapter

                if ( direction == ItemTouchHelper.LEFT) {
                    realm.executeTransaction {
                        AlarmScheduler.cancelReminder(this@EditGoal,reminders[viewHolder.adapterPosition]?.remID?.toInt()!!)
                        reminders.removeAt(viewHolder.adapterPosition)
                        reminderAdapter.submitList(reminders)
                        reminderAdapter.notifyDataSetChanged()
                        adapter.notifyItemRemoved(viewHolder.adapterPosition)
                    }



                }


            }

        }


        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(reminder_recycler)

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


            val id = UUID.randomUUID().leastSignificantBits

            val reminder= Reminder(id,
                ReminderData.reminderDay,
                ReminderData.reminderMonth,
                ReminderData.reminderYear,
                ReminderData.hour,
                ReminderData.minute,
                ReminderData.am_pm, ReminderData.reminderPeriod)

            realm.executeTransaction{
                reminders.add(reminder)
            }


            reminderAdapter.submitList(reminders)

            reminderAdapter.notifyDataSetChanged()
           // AlarmScheduler.scheduleAlarmsForReminder(this, reminder,id.toInt(), ReminderData.reminderPeriod)


        }
    }


    /**
     * Function to update the current Color of the goalList period buttons.
     */
    fun updatePeriodColor(gradient: Drawable){

        when (currentPeriod){

            0->{
                dailyButton.background = gradient
                weeklyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                monthlyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                yearlyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                frequencyTextField.text = "times per day"

            }
            1->{
                dailyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                weeklyButton.background = gradient
                monthlyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                yearlyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                frequencyTextField.text = "times per week"
            }
            2->{
                dailyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                weeklyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                monthlyButton.background = gradient
                yearlyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                frequencyTextField.text = "times per month"
            }
            3->{
                dailyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                weeklyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                monthlyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                yearlyButton.background = gradient
                frequencyTextField.text = "times per year"
            }

        }

    }

    /**
     * Sets up the recyclerView for the Reminders
     */
    private fun initReminderRecyclerView(){


        reminder_recycler.apply {
            layoutManager = LinearLayoutManager(this@EditGoal)
            reminderAdapter = ReminderRecyclerAdapter()
            reminder_recycler.adapter = reminderAdapter
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
