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

import android.content.Context
import android.util.Log
import android.widget.Toast
import at.fhooe.mc.goals.MainActivity
import io.realm.Realm
import io.realm.RealmResults


class GoalsFragment : Fragment() {

    private lateinit var goalsViewModel: GoalsViewModel
    private lateinit var realm: Realm

    val data = ArrayList<Goal>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        goalsViewModel =
            ViewModelProviders.of(this).get(GoalsViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_goals, container, false)

        realm = Realm.getDefaultInstance()

        /*val textView: TextView = root.findViewById(R.id.text_home)
        goalsViewModel.text.observe(this, Observer {
            textView.text = it.name
        })*/

        /*(activity as MainActivity).setFragmentRefreshListener(object : FragmentRefreshListener{
            override fun onRefresh() {

            }
        })*/




        return root
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = activity
        recyclerView.layoutManager = LinearLayoutManager(activity)

        realm.beginTransaction()

        val result = realm.where(Goal::class.java).findAll()

        realm.commitTransaction()
        if (result != null){
            data.addAll(result)
        }



        //testing(result)
        /*data.add(Goal("Quit smoking", false,80, 10, 10))
        data.add(Goal("Learn more", true, 20,  10, 10))*/

        /*recyclerView.addOnItemTouchListener(RecyclertouchListener(this.context!!,recyclerView,
            object : ClickListener {
                override fun onClick(view: View, position: Int) {
                    Log.i("MyTag", "Progress on position $position is ${data[position].progress}")
                    //realm.beginTransaction()

                    data[position].progress = data[position].progress!!+5
                    //realm.commitTransaction()
                    recyclerView.adapter?.notifyItemChanged(position)
                    Toast.makeText(activity,"Single click on $position",Toast.LENGTH_SHORT).show()
                }

                override fun onDoubleClick(view: View, position: Int) {
                    Toast.makeText(activity, "Double tap on position $position", Toast.LENGTH_SHORT).show()
                }
            }))*/

        recyclerView.adapter = RecyclerAdapter(data) { goal: Goal, position: Int -> goalClicked(goal, position) }
    }

    private fun goalClicked(goal: Goal, position: Int) : Boolean{

        realm.beginTransaction()
        data[position].progress = data[position].progress!! + 5
        realm.commitTransaction()

        Log.i("MyTag", "Progress on position $position is ${data[position].progress}")
        recyclerView.adapter?.notifyItemChanged(position)
        Toast.makeText(activity,"Clicked: ${goal.name} at position $position", Toast.LENGTH_SHORT).show()
        return true
    }

/*
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





    }*/
}