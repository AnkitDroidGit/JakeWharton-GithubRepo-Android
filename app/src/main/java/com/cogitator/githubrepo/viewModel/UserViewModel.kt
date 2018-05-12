package com.cogitator.githubrepo.viewModel

import android.arch.lifecycle.ViewModel
import com.cogitator.githubrepo.PayCogApp
import com.cogitator.githubrepo.model.Repo
import com.cogitator.githubrepo.model.UserProfile
import com.cogitator.githubrepo.network.IRetrofit
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author Ankit Kumar (ankitdroiddeveloper@gmail.com) on 12/05/2018 (MM/DD/YYYY)
 */
class UserViewModel : ViewModel() {
    @Inject
    lateinit var api: IRetrofit

    init {
        PayCogApp.appComponent.inject(this)
    }


    fun getUserProfile(userId: String): Observable<UserProfile> {
        return api.getUserProfile(userId)
    }

    fun getRepository(userId: String): Observable<List<Repo>> {
        return api.getUserAllRepository(userId, "all")

    }

//    fun getFollowers(userId: String): Observable<List<Users>> {
//        return api.getUserFollowers(userId)
//    }
//
//    fun getFollowing(userId: String): Observable<List<Users>> {
//        return api.getUserFollowing(userId)
//
//    }

}