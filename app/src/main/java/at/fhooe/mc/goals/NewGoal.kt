package at.fhooe.mc.goals

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import at.fhooe.mc.goals.Database.Goal
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_new_goal.*
import kotlinx.android.synthetic.main.content_new_goal.*




class NewGoal : AppCompatActivity() {

    private lateinit var realm: Realm
    private var build = true
    private var currentPeriod = 0
    private var orange = "#e88317"
    private var green = "#4d9446"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_goal)


        updatePeriodColor(green)
        setThemeGreen()

        dailyButton.setBackgroundColor(Color.parseColor(green))
        realm = Realm.getDefaultInstance()


        setSupportActionBar(toolbar)


        saveButton.setOnClickListener{


            realm.executeTransactionAsync({

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

            },{
                Log.d("Goal", "Saved successfully")
            },{
                Log.d("Goal", "Not saved")
            })

            finish()
        }

        content_new_goal.setOnTouchListener { v, event ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken,0)
            false
        }



        buildButton.setOnClickListener {

            setThemeGreen() // change Theme of the screen
            build = true
            updatePeriodColor(green)


        }



        quitButton.setOnClickListener {

            setThemeOrange()
            build = false
            updatePeriodColor(orange)

        }

        dailyButton.setOnClickListener {

            currentPeriod = 0
            if (build) updatePeriodColor(green) else updatePeriodColor(orange)

        }
        weeklyButton.setOnClickListener {

            currentPeriod = 1
            if (build) updatePeriodColor(green) else updatePeriodColor(orange)
        }
        monthlyButton.setOnClickListener {

            currentPeriod = 2
            if (build) updatePeriodColor(green) else updatePeriodColor(orange)
        }

        yearlyButton.setOnClickListener {

            currentPeriod = 3
            if (build) updatePeriodColor(green) else updatePeriodColor(orange)
        }



    }


    /**
     * Function to update the current Color of the goal period buttons.
     */
    fun updatePeriodColor(color: String){

        when (currentPeriod){

            0->{
                dailyButton.setBackgroundColor(Color.parseColor(color))
                weeklyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                monthlyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                yearlyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                frequencyTextField.text = "times per day"

            }
            1->{
                dailyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                weeklyButton.setBackgroundColor(Color.parseColor(color))
                monthlyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                yearlyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                frequencyTextField.text = "times per week"
            }
            2->{
                dailyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                weeklyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                monthlyButton.setBackgroundColor(Color.parseColor(color))
                yearlyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                frequencyTextField.text = "times per month"
            }
            3->{
                dailyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                weeklyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                monthlyButton.setBackgroundColor(Color.parseColor("lightgrey"))
                yearlyButton.setBackgroundColor(Color.parseColor(color))
                frequencyTextField.text = "times per year"
            }

        }

    }



    /**
     * Function to set the theme of the NewGoalScreen to Green (Build-Goal)
     */
    fun setThemeGreen(){

        toolbar_layout.setContentScrimColor(Color.parseColor(green))
        toolbar.setBackgroundColor(Color.parseColor(green))
        app_bar.setBackgroundColor(Color.parseColor(green))
        toolbar_layout.setBackgroundColor(Color.parseColor(green))
        goalNameTextView.setTextColor(Color.parseColor(green))
        buildButton.setBackgroundColor(Color.parseColor(green))
        quitButton.setBackgroundColor(Color.parseColor("lightgray"))
        goalPeriodTextView.setTextColor(Color.parseColor(green))
        goalTypeView.setTextColor(Color.parseColor(green))
        achieveTextField.setTextColor(Color.parseColor(green))
        //reminderTexfield.setTextColor(Color.parseColor(green))

    }

    /**
     * Function to set the theme of the NewGoalScreen to Green (Build-Goal)
     */
    fun setThemeOrange(){
        toolbar_layout.setContentScrimColor(Color.parseColor(orange))
        toolbar.setBackgroundColor(Color.parseColor(orange))
        app_bar.setBackgroundColor(Color.parseColor(orange))
        toolbar_layout.setBackgroundColor(Color.parseColor(orange))
        goalNameTextView.setTextColor(Color.parseColor(orange))
        quitButton.setBackgroundColor(Color.parseColor(orange))
        buildButton.setBackgroundColor(Color.parseColor("lightgray"))
        goalTypeView.setTextColor(Color.parseColor(orange))
        achieveTextField.setTextColor(Color.parseColor(orange))
        goalPeriodTextView.setTextColor(Color.parseColor(orange))
        //reminderTexfield.setTextColor(Color.parseColor(orange))

    }




}
