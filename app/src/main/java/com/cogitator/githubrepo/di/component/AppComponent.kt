package com.cogitator.githubrepo.di.component

import com.cogitator.githubrepo.di.module.AppModule
import com.cogitator.githubrepo.di.module.NetworkModule
import com.cogitator.githubrepo.viewModel.UserViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * @author Ankit Kumar (ankitdroiddeveloper@gmail.com) on 12/05/2018 (MM/DD/YYYY)
 */

@Singleton
@Component(modules = [(AppModule::class), (NetworkModule::class)])
interface AppComponent {
    fun inject(appModule: AppModule)
    fun inject(networkModule: NetworkModule)

    fun inject(userViewModel: UserViewModel)
}