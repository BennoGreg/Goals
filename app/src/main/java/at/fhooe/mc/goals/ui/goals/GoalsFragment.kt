package at.fhooe.mc.goals.ui.goals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import at.fhooe.mc.goals.Database.Goal
import at.fhooe.mc.goals.R
import kotlinx.android.synthetic.main.fragment_goals.*

class GoalsFragment : Fragment() {

    private lateinit var goalsViewModel: GoalsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        goalsViewModel =
            ViewModelProviders.of(this).get(GoalsViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_goals, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_home)
        goalsViewModel.text.observe(this, Observer {
            textView.text = it.name
        })*/


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = activity
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val data = ArrayList<Goal>()
        data.add(Goal("Quit smoking", false,0, 10, 10))
        data.add(Goal("Learn more", true, 0,  10, 10))

        recyclerView.adapter = RecyclerAdapter(data)
    }
}