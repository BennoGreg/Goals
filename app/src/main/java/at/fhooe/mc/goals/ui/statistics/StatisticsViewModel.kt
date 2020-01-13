package at.fhooe.mc.goals.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StatisticsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "\nThis is statistics Fragment.\n\nThis fragment will be implemented later on. "
    }
    val text: LiveData<String> = _text
}