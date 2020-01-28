package at.fhooe.mc.goals.Database

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class StatisticData(
    @PrimaryKey
    var sID: Int = 0,
    //val timeStamp: Long = System.currentTimeMillis(),
    var nrOfAchievedDaily: Int = 0,
    var nrOfTotalDaily: Int = 0,
    var nrOfAchievedWeekly: Int = 0,
    var nrOfTotalWeekly: Int = 0,
    var nrOfAchievedMonthly: Int = 0,
    var nrOfTotalMonthly: Int = 0,
    var nrOfAchievedYearly: Int = 0,
    var nrOfTotalYearly: Int = 0,
    var nrOfTotal: Int = 0,
    var nrOfTotalAchieved: Int = 0

) : RealmObject() {
}