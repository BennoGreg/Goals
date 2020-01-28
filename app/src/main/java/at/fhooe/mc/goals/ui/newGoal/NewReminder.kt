package at.fhooe.mc.goals.ui.newGoal

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import at.fhooe.mc.goals.R
import kotlinx.android.synthetic.main.activity_new_reminder.*
import kotlinx.android.synthetic.main.content_new_goal.*
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class NewReminder : AppCompatActivity() {


    var selectedDay = Calendar.DAY_OF_MONTH
    var selectedMonth = Calendar.MONTH
    var selectedYear = Calendar.YEAR



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_reminder)


        updateRepeatButtons("never")


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
            ReminderData.reminderMonth= selectedMonth
            ReminderData.reminderYear = selectedYear
            finish()
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
            }
            "daily" -> {
                neverButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatDailyButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
                repeatWeeklyButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatMonthlyButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatYearlyButton.setBackgroundColor(Color.parseColor("lightgray"))
            }
            "weekly" -> {
                neverButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatDailyButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatWeeklyButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
                repeatMonthlyButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatYearlyButton.setBackgroundColor(Color.parseColor("lightgray"))
            }
            "monthly" -> {
                neverButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatDailyButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatWeeklyButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatMonthlyButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
                repeatYearlyButton.setBackgroundColor(Color.parseColor("lightgray"))
            }
            "yearly" -> {
                neverButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatDailyButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatWeeklyButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatMonthlyButton.setBackgroundColor(Color.parseColor("lightgray"))
                repeatYearlyButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            }
        }
    }
}
