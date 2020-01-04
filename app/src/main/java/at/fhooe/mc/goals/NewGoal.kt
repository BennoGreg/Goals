package at.fhooe.mc.goals

import android.app.PendingIntent.getActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_goal)


        realm = Realm.getDefaultInstance()

        realm.beginTransaction()
        realm.deleteAll()
        realm.commitTransaction()


        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

        }

        saveButton.setOnClickListener {

            /*realm.beginTransaction()
            var goal:String? = editText.text.toString()
            var newGoal = Goal(goal)
            realm.copyToRealmOrUpdate(newGoal)
            realm.commitTransaction()*/

            realm.executeTransactionAsync({
                val goal = it.createObject(Goal::class.java)
                goal.name = editText.text.toString()

            },{
                Log.d("MyTag", "Saved successfully")
            },{
                Log.d("MyTag", "Not saved")
            })
            realm.executeTransactionAsync({
                val goal = it.createObject(Goal::class.java)
                goal.name = editText.text.toString()

            },{
                Log.d("MyTag", "Saved successfully")
            },{
                Log.d("MyTag", "Not saved")
            })

        }

        showButton.setOnClickListener {

            /*realm.beginTransaction()

            val result: Goal? = realm.where(Goal::class.java).findFirst()
            showData.text = result?.name
            realm.commitTransaction()*/

            val goals = realm.where(Goal::class.java).findAll()
            showData.text = goals[0]?.name + goals[1]?.name

        }


    }


}
