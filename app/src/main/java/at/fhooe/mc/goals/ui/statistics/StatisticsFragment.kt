package at.fhooe.mc.goals.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import at.fhooe.mc.goals.Database.StatisticData
import at.fhooe.mc.goals.R
import io.realm.Realm
import io.realm.kotlin.where
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

        realm = Realm.getDefaultInstance()


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        realm.beginTransaction()

        val result = realm.where<StatisticData>().findFirst()


        if (result != null){

            //Progressdaily
            setProgressBar(progressBarDaily,tv_dailyGoals,result.nrOfArchievedDaily,result.nrOfTotalDaily,"Day")

            //Progressweekly
            setProgressBar(progressBarWeekly,tv_weeklyGoals,result.nrOfArchievedWeekly,result.nrOfTotalWeekly,"Week")

            //Progressmonthly
            setProgressBar(progressBarMonthly,tv_monthlyGoals,result.nrOfArchievedMonthly,result.nrOfTotalMonthly,"Month")

            //Progressyearly
            setProgressBar(progressBarYearly,tv_yearlyGoals,result.nrOfArchievedYearly,result.nrOfTotalYearly,"Year")

            //Progresstotal
            setProgressBar(progressBarTotal,tv_totalGoals,result.nrOfTotalYearly,result.nrOfTotal,"Year")

        }

        realm.commitTransaction()
    }

    fun setProgressBar(progressBar: ProgressBar, textView: TextView, progress: Int, quantity: Int, period: String){

        progressBar.progress = progress
        textView.text =
            this.resources.getString(R.string.statisticProgressInText,progress,quantity,period)
    }
}