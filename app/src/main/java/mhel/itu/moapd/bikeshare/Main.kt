package mhel.itu.moapd.bikeshare
import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import mhel.itu.moapd.bikeshare.model.entity.Account
import mhel.itu.moapd.bikeshare.model.repository.AccountRepository

class Main : Application() {
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        Realm.setDefaultConfiguration(
            RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build());
        //Realm.getDefaultInstance().deleteAll()
    }
}
