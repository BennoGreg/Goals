package at.fhooe.mc.goals.ui.goals

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import at.fhooe.mc.goals.Database.Goal
import at.fhooe.mc.goals.R
import kotlinx.android.synthetic.main.fragment_goals.*
import android.view.MotionEvent
import android.view.GestureDetector
import at.fhooe.mc.goals.ui.goals.GoalsFragment.ClickListener
import android.content.Context
import android.widget.Toast
import at.fhooe.mc.goals.MainActivity


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
        data.add(Goal("Quit smoking", false,80, 10, 10))
        data.add(Goal("Learn more", true, 20,  10, 10))

        recyclerView.addOnItemTouchListener(RecyclertouchListener(this.context!!,recyclerView,
            object : ClickListener {
                override fun onClick(view: View, position: Int) {
                    Toast.makeText(activity,"Single click on $position",Toast.LENGTH_SHORT).show()
                }

                override fun onDoubleClick(view: View, position: Int) {
                    Toast.makeText(activity, "Double tap on position $position", Toast.LENGTH_SHORT).show()
                }
            }))

        recyclerView.adapter = RecyclerAdapter(data)
    }

    companion object interface ClickListener {

        public fun onClick(view: View, position: Int)
        public fun onDoubleClick(view: View, position: Int)
    }

    class RecyclertouchListener(context: Context, val recyclerView: RecyclerView, val clicklistener:ClickListener ) : RecyclerView.OnItemTouchListener{


        private var gestureDetector: GestureDetector

        init {
            gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener(){
                override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                    return true

                }

                override fun onLongPress(e: MotionEvent?) {
                    if (e != null){
                        var child = recyclerView.findChildViewUnder(e.x,e.y);
                        if ((child != null) ){
                            clicklistener.onDoubleClick(child,  recyclerView.getChildAdapterPosition(child))
                        }
                    }

                }
            })
        }


        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

        }

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            val child = rv.findChildViewUnder(e.x,e.y)
            if (child != null){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child))
            }
            return false
        }

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

        }

    }
}