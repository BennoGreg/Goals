package at.fhooe.mc.goals.ui.settings


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import at.fhooe.mc.goals.R
import kotlinx.android.synthetic.main.fragment_settings.*
import io.realm.Realm
import android.app.AlertDialog
import android.app.NotificationManager
import at.fhooe.mc.goals.Database.Reminder
import at.fhooe.mc.goals.Database.StatisticData
import at.fhooe.mc.goals.MainActivity
import at.fhooe.mc.goals.ui.statistics.StatisticsSingleton
import at.fhooe.mc.goals.ui.newGoal.Reminder.AlarmScheduler


class SettingsFragment : Fragment(), View.OnClickListener {

    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var realm: Realm

    override fun onClick(v: View?) {


        AlertDialog.Builder(context).setMessage(R.string.delete_data_text)
            .setPositiveButton(R.string.confirm_delete_data) { _, _ ->

                run {
                    realm.beginTransaction()
                    val notificationManager =
                        context!!.getSystemService(NotificationManager::class.java)


                    val result = realm.where(Reminder::class.java).findAll()
                    for (reminder in result) {
                        AlarmScheduler.cancelReminder(context!!, reminder.remID.toInt())
                        notificationManager.cancel(reminder.remID.toInt())
                    }



                    realm.deleteAll()

                    StatisticsSingleton.stats = StatisticData()

                    realm.commitTransaction()
                }
            }.setNegativeButton(R.string.cancel_delete_data) { _, _ ->

        }.create().show()

    }


    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        realm = Realm.getDefaultInstance()
        settingsViewModel =
            ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)

        val main = activity as MainActivity
        main.fab.hide()

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        deleteDataButton.setOnClickListener(this)
    }
}