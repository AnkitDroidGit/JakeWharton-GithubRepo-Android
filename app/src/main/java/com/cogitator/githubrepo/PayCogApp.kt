package com.cogitator.githubrepo

import android.app.Application
import android.content.Context
import com.cogitator.githubrepo.di.component.AppComponent
import com.cogitator.githubrepo.di.component.DaggerAppComponent
import com.cogitator.githubrepo.di.module.AppModule
import com.cogitator.githubrepo.di.module.NetworkModule
import com.cogitator.githubrepo.model.data.UserProfile
import io.realm.Realm
import io.realm.RealmConfiguration


/**
 * @author Ankit Kumar (ankitdroiddeveloper@gmail.com) on 12/05/2018 (MM/DD/YYYY)
 */
class PayCogApp : Application() {
    private lateinit var context: Context

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
//        Realm.init(this)
//        val realmConfig = RealmConfiguration.Builder()
//                .initialData { realm -> realm.createObject(UserProfile::class.java) }
//                .build()
//        Realm.deleteRealm(realmConfig) // Delete Realm between app restarts.
//        Realm.setDefaultConfiguration(realmConfig)

        appComponent = DaggerAppComponent.builder()
                .networkModule(NetworkModule(this))
                .appModule(AppModule(this))
//                .dBModule(DBModule(this))
                .build()

        context = applicationContext
    }

    fun getContext(): Context {
        return context
    }

}