package at.fhooe.mc.goals.ui.newGoal.Reminder

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import at.fhooe.mc.goals.R
import kotlinx.android.synthetic.main.activity_new_reminder.*
import java.util.*

class NewReminder : AppCompatActivity() {


    var selectedDay = Calendar.DAY_OF_MONTH
    var selectedMonth = Calendar.MONTH
    var selectedYear = Calendar.YEAR

    var selectedMinute = Calendar.MINUTE
    var selectedHour = Calendar.HOUR
    var selectedAM_PM = "am_pm"

    var reminderPeriod = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_reminder)

        OnClickTime()

        updateRepeatButtons("never")

        neverButton.setOnClickListener {
            updateRepeatButtons("never")
        }


        repeatDailyButton.setOnClickListener {
            updateRepeatButtons("daily")
        }
        repeatWeeklyButton.setOnClickListener {
            updateRepeatButtons("weekly")

        }
        repeatMonthlyButton.setOnClickListener {
            updateRepeatButtons("monthly")

        }

        repeatYearlyButton.setOnClickListener {
            updateRepeatButtons("yearly")
        }



            val datePicker = findViewById<DatePicker>(R.id.datePicker)
            val today = Calendar.getInstance()
            datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH)
            )
            { view, year, month, day ->
                val month = month + 1

                selectedDay = day
                selectedMonth = month
                selectedYear = year

                val msg = "You Selected: $day/$month/$year"

                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            }


        saveReminderButton.setOnClickListener {

            ReminderData.reminderDay = selectedDay
            ReminderData.reminderMonth = selectedMonth
            ReminderData.reminderYear = selectedYear
            ReminderData.minute = selectedMinute
            ReminderData.hour = selectedHour
            ReminderData.am_pm = selectedAM_PM
            ReminderData.reminderPeriod = reminderPeriod
            finish()
        }




    }

    /**
     * Function to save the selected time
     */
    private fun OnClickTime() {


        val timePicker = findViewById<TimePicker>(R.id.timePicker)
        timePicker.setOnTimeChangedListener { _, hour, minute -> var hour = hour
            var am_pm = ""
            // AM_PM decider logic
            when {hour == 0 -> { hour += 12
                am_pm = "AM"
            }
                hour == 12 -> am_pm = "PM"
                hour > 12 -> { hour -= 12
                    am_pm = "PM"
                }
                else -> am_pm = "AM"
            }

                val finalHour = if (hour < 10) "0" + hour else hour
                val min = if (minute < 10) "0" + minute else minute
                // display format of time


           selectedMinute = Integer.parseInt(min.toString())
            selectedHour = Integer.parseInt(finalHour.toString())
            selectedAM_PM = am_pm




        }
    }


    /**
     * Updates the colors of the repeat buttons when the user changes his choice
     */
    fun updateRepeatButtons(repeatPeriod: String){

        when (repeatPeriod) {

            "never" -> {
                neverButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
                repeatDailyButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatWeeklyButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatMonthlyButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatYearlyButton.setBackgroundColor(Color.parseColor("lightgray"))
                reminderPeriod = 0
            }
            "daily" -> {
                neverButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatDailyButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
                repeatWeeklyButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatMonthlyButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatYearlyButton.setBackgroundColor(Color.parseColor("lightgray"))
                reminderPeriod = 1
            }
            "weekly" -> {
                neverButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatDailyButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatWeeklyButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
                repeatMonthlyButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatYearlyButton.setBackgroundColor(Color.parseColor("lightgray"))
                reminderPeriod = 2
            }
            "monthly" -> {
                neverButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatDailyButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatWeeklyButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatMonthlyButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
                repeatYearlyButton.setBackgroundColor(Color.parseColor("lightgray"))
                reminderPeriod = 3
            }
            "yearly" -> {
                neverButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatDailyButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatWeeklyButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatMonthlyButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatYearlyButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
                reminderPeriod = 4
            }

        }
    }
}
