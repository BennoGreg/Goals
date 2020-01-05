package at.fhooe.mc.goals.Database

import io.realm.DynamicRealm
import io.realm.RealmMigration
import java.util.*

class MyMigration : RealmMigration{
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        val schema = realm.schema

        if (oldVersion == 0L){
            schema.get("Goal")?.addField("progress", Int::class.java)?.setNullable("progress",true)
            oldVersion.inc()
        }

    }
}