package at.fhooe.mc.goals.Database

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder().schemaVersion(6).migration(MyMigration()).build()
        Realm.setDefaultConfiguration(config)


    }
}
