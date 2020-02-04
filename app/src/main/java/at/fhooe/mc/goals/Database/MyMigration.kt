package at.fhooe.mc.goals.Database

import io.realm.DynamicRealm
import io.realm.FieldAttribute
import io.realm.RealmMigration

class MyMigration : RealmMigration {
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        val schema = realm.schema

        if (oldVersion == 0L) {
            schema.get("Goal")?.addField("progress", Int::class.java)?.setNullable("progress", true)
            oldVersion.inc()
        }

        if (oldVersion == 2L) {
            schema.create("StatisticData")
                .addField("sID", Int::class.java, FieldAttribute.PRIMARY_KEY)
                .addField("nrOfArchievedDaily", Int::class.java)
                .addField("nrOfTotalDaily", Int::class.java)
                .addField("nrOfArchievedWeekly", Int::class.java)
                .addField("nrOfTotalWeekly", Int::class.java)
                .addField("nrOfArchievedMonthly", Int::class.java)
                .addField("nrOfTotalMonthly", Int::class.java)
                .addField("nrOfArchievedYearly", Int::class.java)
                .addField("nrOfTotalYearly", Int::class.java)
                .addField("nrOfTotal", Int::class.java)

            oldVersion.inc()
        }

        if (oldVersion == 3L) {
            schema.get("StatisticData")?.renameField("nrOfArchievedDayly", "nrOfAchievedDaily")
                ?.renameField("nrOfTotalDayly", "nrOfTotalDaily")
        }

        if (oldVersion == 4L) {
            schema.get("StatisticData")?.addField("nrOfTotalAchieved", Int::class.java)
                ?.renameField("nrOfArchievedDaily", "nrOfAchievedDaily")
                ?.renameField("nrOfArchievedWeekly", "nrOfAchievedWeekly")
                ?.renameField("nrOfArchievedMonthly", "nrOfAchievedMonthly")
                ?.renameField("nrOfArchievedYearly", "nrOfAchievedYearly")

        }

        if (oldVersion == 5L) {

            schema.create("Reminder")
                .addField("remID", Long::class.java, FieldAttribute.PRIMARY_KEY)
                .addField("reminderDay", Int::class.java)
                .addField("reminderMonth", Int::class.java)
                .addField("reminderYear", Int::class.java)
                .addField("hour", Int::class.java)
                .addField("minute", Int::class.java)
                .addField("am_pm", String::class.java)
                .addField("reminderPeriod", Int::class.java)

            schema.get("Goal")?.addRealmListField("reminderList", schema.get("Reminder")!!)


        }

    }
}