package at.fhooe.mc.goals

import android.app.PendingIntent.getActivity
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import at.fhooe.mc.goals.Database.Goal
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_new_goal.*
import kotlinx.android.synthetic.main.content_new_goal.*




class NewGoal : AppCompatActivity() {

    private lateinit var realm: Realm
    private var build = true
    private var currentPeriod = 0
    private var orange = "#FF4500"
    private var green = "#006400"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_goal)



        setThemeGreen()
        dailyButton.setBackgroundColor(Color.parseColor(green))
        realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.deleteAll()
        realm.commitTransaction()


        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

        }



        buildButton.setOnClickListener {

            setThemeGreen() // change Theme of the screen
            build = true
            updatePeriodColor(green)


            realm.executeTransactionAsync({
                val goal = it.createObject(Goal::class.java)
              //  goal.name = editText.text.toString()

            },{
                Log.d("MyTag", "Saved successfully")
            },{
                Log.d("MyTag", "Not saved")
            })
            realm.executeTransactionAsync({
                val goal = it.createObject(Goal::class.java)
              //  goal.name = editText.text.toString()

            },{
                Log.d("MyTag", "Saved successfully")
            },{
                Log.d("MyTag", "Not saved")
            })

        }



        quitButton.setOnClickListener {

            setThemeOrange()
            build = false
            updatePeriodColor(orange)

            /*realm.beginTransaction()

            val result: Goal? = realm.where(Goal::class.java).findFirst()
            showData.text = result?.name
            realm.commitTransaction()*/

            val goals = realm.where(Goal::class.java).findAll()
           // showData.text = goals[0]?.name + goals[1]?.name

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

    fun updateAchieveText(){

    }

    /**
     * Function to set the theme of the NewGoalScreen to Green (Build-Goal)
     */
    fun setThemeGreen(){
        buildButton.setBackgroundColor(Color.parseColor(green))
        quitButton.setBackgroundColor(Color.parseColor("lightgray"))
        goalPeriodTextView.setTextColor(Color.parseColor(green))
        goalTypeView.setTextColor(Color.parseColor(green))
        achieveTextField.setTextColor(Color.parseColor(green))
        reminderTexfield.setTextColor(Color.parseColor(green))

    }

    /**
     * Function to set the theme of the NewGoalScreen to Green (Build-Goal)
     */
    fun setThemeOrange(){
        quitButton.setBackgroundColor(Color.parseColor(orange))
        buildButton.setBackgroundColor(Color.parseColor("lightgray"))
        goalTypeView.setTextColor(Color.parseColor(orange))
        achieveTextField.setTextColor(Color.parseColor(orange))
        reminderTexfield.setTextColor(Color.parseColor(orange))
        goalPeriodTextView.setTextColor(Color.parseColor(orange))

    }


}
