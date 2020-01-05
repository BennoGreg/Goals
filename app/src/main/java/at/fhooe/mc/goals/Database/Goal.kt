package at.fhooe.mc.goals.Database

import io.realm.RealmObject

open class Goal(
    var name: String? = null,
    var buildQuit: Boolean? = null,
    var progress: Int? = null,
    var goalPeriod: Int? = null,
    var goalFrequency: Int? = null) : RealmObject() {
}