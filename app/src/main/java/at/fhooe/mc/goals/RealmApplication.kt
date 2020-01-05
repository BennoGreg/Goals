package at.fhooe.mc.goals

import android.app.Application
import at.fhooe.mc.goals.Database.MyMigration
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmApplication :Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder().schemaVersion(2).migration(MyMigration()).build()
        Realm.setDefaultConfiguration(config)


    }
}
