package com.cogitator.githubrepo.di.module

import android.app.Application
import android.content.Context
import com.cogitator.githubrepo.PayConiqApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * @author Ankit Kumar (ankitdroiddeveloper@gmail.com) on 12/05/2018 (MM/DD/YYYY)
 */
@Module
class AppModule(val app: PayConiqApp) {
    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideApplication(): Application = app


}