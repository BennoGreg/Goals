package at.fhooe.mc.goals.ui.settings

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import at.fhooe.mc.goals.R
import kotlinx.android.synthetic.main.fragment_settings.*
import androidx.core.content.ContextCompat.getSystemService
import io.realm.Realm
import android.R.attr.button
import android.app.AlertDialog
import kotlinx.android.synthetic.main.settings_delete_popup.*
import kotlinx.android.synthetic.main.settings_delete_popup.view.*
import java.util.zip.Inflater


class SettingsFragment : Fragment(), View.OnClickListener {


    override fun onClick(v: View?) {


        AlertDialog.Builder(context).setMessage("Do your really want to delete the entire data?\nThis can not be undone.").setPositiveButton("Confirm"){
            _,_->

            run {
                realm.beginTransaction()

                realm.deleteAll()

                realm.commitTransaction()
            }
        }.setNegativeButton("Cancel"){
            _,_ ->

        }.create().show()


/*
        Toast.makeText(this.context,"Button pressed",Toast.LENGTH_SHORT).show()
        val inflater =
            context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Inflate a custom view using layout inflater
        val view = inflater.inflate(R.layout.settings_delete_popup,null)

        // Initialize a new instance of popup window
        val popupWindow = PopupWindow(
            view, // Custom view to show in popup window
            LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
            LinearLayout.LayoutParams.WRAP_CONTENT // Window height
        )



                // Set an elevation for the popup window
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    popupWindow.elevation = 10.0F
                }


                // If API level 23 or higher then execute the code
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    // Create a new slide animation for popup window enter transition
                    val slideIn = Slide()
                    slideIn.slideEdge = Gravity.TOP
                    popupWindow.enterTransition = slideIn

                    // Slide animation for popup window exit transition
                    val slideOut = Slide()
                    slideOut.slideEdge = Gravity.RIGHT
                    popupWindow.exitTransition = slideOut

                }

                // Get the widgets reference from custom view
                val tv = view.findViewById<TextView>(R.id.confirmDeleteTextView)
                //val buttonPopup = view.findViewById<Button>(R.id.deleteDataButton)

                // Set click listener for popup window's text view
                tv.setOnClickListener{
                    // Change the text color of popup window's text view
                    tv.setTextColor(Color.RED)
                }

                // Set a click listener for popup's button widget
                view.cancelDeleteButton.setOnClickListener{
                    popupWindow.dismiss()
                }

                view.confirmDeleteButton.setOnClickListener {
                    realm.beginTransaction()

                    realm.deleteAll()

                    realm.commitTransaction()
                    popupWindow.dismiss()
                }


                // Finally, show the popup window on app
                TransitionManager.beginDelayedTransition(settings_layout)
                popupWindow.showAtLocation(
                    settings_layout, // Location to display popup window
                    Gravity.CENTER, // Exact position of layout to display popup
                    0, // X offset
                    0 // Y offset
                )
*/

    }


    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var realm: Realm
    var button: Button? = null


    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        realm = Realm.getDefaultInstance()
        settingsViewModel =
            ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        val textView: TextView = root.findViewById(R.id.text_tools)
        settingsViewModel.text.observe(this, Observer {
            textView.text = it
        })


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        deleteDataButton.setOnClickListener(this)
    }
}