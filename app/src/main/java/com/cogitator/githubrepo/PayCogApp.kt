package com.cogitator.githubrepo

import android.app.Application
import com.cogitator.githubrepo.di.component.AppComponent
import com.cogitator.githubrepo.di.module.AppModule
import com.cogitator.githubrepo.di.module.NetworkModule
import com.cogitator.githubrepo.di.component.DaggerAppComponent


/**
 * @author Ankit Kumar (ankitdroiddeveloper@gmail.com) on 12/05/2018 (MM/DD/YYYY)
 */
class PayCogApp : Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .networkModule(NetworkModule(this))
                .appModule(AppModule(this))
//                .dBModule(DBModule(this))
                .build()


    }
}