package at.fhooe.mc.goals.Database

import android.os.Parcelable
import io.realm.RealmList
import io.realm.RealmObject
import java.io.Serializable

open class Goal(
    var name: String? = null,
    var buildQuit: Boolean? = null,
    var progress: Int? = null,
    var goalPeriod: Int? = null,
    var goalFrequency: Int? = null,
    var reminderList: RealmList<Reminder>? = RealmList()) : RealmObject(),Serializable{
}