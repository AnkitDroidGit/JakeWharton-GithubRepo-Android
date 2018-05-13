package com.cogitator.githubrepo.network

import com.cogitator.githubrepo.model.data.Repo
import com.cogitator.githubrepo.model.data.UserProfile
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author Ankit Kumar (ankitdroiddeveloper@gmail.com) on 12/05/2018 (MM/DD/YYYY)
 */

interface IRetrofit {

    @GET("/users/{user}")
    fun getUserProfile(@Path("user") user: String): Observable<UserProfile>


    @GET("/users/{user}/repos")
    fun getUserAllRepository(@Path("user") user: String, @Query("per_page") count: Int, @Query("page") page: Int): Observable<List<Repo>>

//
//    @GET("/users/{user}/followers")
//    fun getUserFollowers(@Path("user") user: String): Observable<List<Users>>
//
//    @GET("/users/{user}/following")
//    fun getUserFollowing(@Path("user") user: String): Observable<List<Users>>


}