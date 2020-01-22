package at.fhooe.mc.goals

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MotionEvent
import io.realm.Realm
import at.fhooe.mc.goals.Database.Goal
import at.fhooe.mc.goals.Database.StatisticData
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var realm: Realm



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        realm = Realm.getDefaultInstance()



        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.saveButton)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            val i = Intent(this,NewGoal::class.java)
            i.putExtra("newGoal",0)

            startActivityForResult(i, 0)
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_goals, R.id.nav_statistics, R.id.nav_slideshow,
                R.id.nav_settings, R.id.nav_about, R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        realm.beginTransaction()
        var result = realm.where(StatisticData::class.java).findFirst()
        if(result!=null){
            val stat = StatisticData()
            val managedstat = realm.copyFromRealm(stat)
        }
        realm.commitTransaction()

        val pref = getSharedPreferences("lastLoginTime",Context.MODE_PRIVATE)

        val lastTime = pref.getLong("lastLoginTime",System.currentTimeMillis())

        val actual = System.currentTimeMillis()
        val lastCalendar = Calendar.getInstance()
        lastCalendar.timeInMillis = lastTime

        val currentCalendar = Calendar.getInstance()
        currentCalendar.timeInMillis = actual

        if (lastCalendar.get(Calendar.DAY_OF_YEAR) != currentCalendar.get(Calendar.DAY_OF_YEAR)){
            realm.beginTransaction()
            val result = realm.where(Goal::class.java).findAll()
            for(goal in result){
                updateGoal(goal, currentCalendar)
            }
            realm.commitTransaction()
        }

        val edt = pref.edit()


        edt.putLong("lastLoginTime",actual)
        currentCalendar.timeInMillis = actual

        edt.commit()




    }

    private fun updateGoal(goal: Goal, current: Calendar){

        val period = goal.goalPeriod as Int

        val weekDay = current.get(Calendar.DAY_OF_WEEK)

        val monthDay = current.get(Calendar.DAY_OF_MONTH)

        val yearDay = current.get(Calendar.DAY_OF_YEAR)

        when(period){

            0 -> goal.progress = 0

            1 -> {
                if (weekDay == Calendar.MONDAY) goal.progress = 0
            }
            2 -> {
                if (monthDay == 1) goal.progress = 0
            }
            3 -> if (yearDay == 1) goal.progress = 0
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0){

            finish()
            startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }


}



