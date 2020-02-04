package at.fhooe.mc.goals.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import at.fhooe.mc.goals.MainActivity
import at.fhooe.mc.goals.R
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_statistics.*

class StatisticsFragment : Fragment() {

    private lateinit var statisticsViewModel: StatisticsViewModel

    private lateinit var realm: Realm

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        statisticsViewModel =
            ViewModelProviders.of(this).get(StatisticsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_statistics, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_gallery)
        statisticsViewModel.text.observe(this, Observer {
            textView.text = it
        })*/

        val activity = activity as MainActivity
        activity.fab.hide()

        realm = Realm.getDefaultInstance()


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //realm.beginTransaction()

        val mainActivity = activity as MainActivity
        val result = StatisticsSingleton.stats




        if (result != null){

            //Progressdaily
            setProgressBar(progressBarDaily,tv_dailyGoals,result.nrOfAchievedDaily,result.nrOfTotalDaily,resources.getString(R.string.daily_progbar))

            //Progressweekly
            setProgressBar(progressBarWeekly,tv_weeklyGoals,result.nrOfAchievedWeekly,result.nrOfTotalWeekly,resources.getString(R.string.weekly_progbar))

            //Progressmonthly
            setProgressBar(progressBarMonthly,tv_monthlyGoals,result.nrOfAchievedMonthly,result.nrOfTotalMonthly,resources.getString(R.string.monthly_progbar))

            //Progressyearly
            setProgressBar(progressBarYearly,tv_yearlyGoals,result.nrOfAchievedYearly,result.nrOfTotalYearly,resources.getString(R.string.yearly_progbar))

            //Progresstotal
            setProgressBar(progressBarTotal,tv_totalGoals,result.nrOfTotalAchieved,result.nrOfTotal,resources.getString(R.string.total_progbar))

        }

        //realm.commitTransaction()
    }

    fun setProgressBar(progressBar: ProgressBar, textView: TextView, progress: Int, quantity: Int, period: String){

        progressBar.max = quantity
        progressBar.progress = progress
        textView.text =
            this.resources.getString(R.string.statisticProgressInText,progress,quantity,period)
    }
}