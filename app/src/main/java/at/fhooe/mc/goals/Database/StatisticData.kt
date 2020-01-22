package at.fhooe.mc.goals.Database

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class StatisticData(
    @PrimaryKey
    var sID: Int = 0,
    //val timeStamp: Long = System.currentTimeMillis(),
    var nrOfArchievedDayly: Int = 0,
    var nrOfTotalDayly: Int = 0,
    var nrOfArchievedWeekly: Int = 0,
    var nrOfTotalWeekly: Int = 0,
    var nrOfArchievedMonthly: Int = 0,
    var nrOfTotalMonthly: Int = 0,
    var nrOfArchievedYearly: Int = 0,
    var nrOfTotalYearly: Int = 0,
    var nrOfTotal: Int = 0

) : RealmObject() {
}