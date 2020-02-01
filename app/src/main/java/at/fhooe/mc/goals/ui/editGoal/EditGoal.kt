package at.fhooe.mc.goals.ui.editGoal

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import at.fhooe.mc.goals.Database.Goal
import at.fhooe.mc.goals.Database.Reminder
import at.fhooe.mc.goals.R
import at.fhooe.mc.goals.StatisticsSingleton
import at.fhooe.mc.goals.ui.goals.RecyclerAdapter
import at.fhooe.mc.goals.ui.newGoal.Reminder.ReminderRecyclerAdapter

import io.realm.Realm
import io.realm.RealmList
import kotlinx.android.synthetic.main.activity_edit_goal.*
import kotlinx.android.synthetic.main.activity_edit_goal.app_bar
import kotlinx.android.synthetic.main.activity_edit_goal.toolbar
import kotlinx.android.synthetic.main.activity_edit_goal.toolbar_layout
import kotlinx.android.synthetic.main.content_edit_goal.*
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


class EditGoal : AppCompatActivity() {

    lateinit var realm: Realm

    private var build = true
    private var currentPeriod = 0
    private var orange = "#e88317"
    lateinit var orangeGradient: Drawable
    private var green = "#4d9446"
    lateinit var greenGradient: Drawable
    lateinit var list: RealmList<Reminder>
    lateinit var reminderAdapter: ReminderRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_goal)
        setSupportActionBar(toolbar)

        val position = intent.getIntExtra("position",0)

        val goal = GoalSingleton.getGoal(position)

        list = goal.reminderList!!

        currentPeriod = goal.goalPeriod!!
        val oldPeriod = currentPeriod


        initReminderRecyclerView()

        reminderAdapter.submitList(list)
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
