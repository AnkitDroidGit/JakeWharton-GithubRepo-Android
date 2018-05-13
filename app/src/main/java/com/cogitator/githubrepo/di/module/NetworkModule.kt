package com.cogitator.githubrepo.di.module

import com.cogitator.githubrepo.PayConiqApp
import com.cogitator.githubrepo.network.IRetrofit
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import javax.inject.Singleton

/**
 * @author Ankit Kumar (ankitdroiddeveloper@gmail.com) on 12/05/2018 (MM/DD/YYYY)
 */

@Module
class NetworkModule(private val payCogApp: PayConiqApp) {

    @Provides
    @Singleton
    fun getRetrofit(): IRetrofit {
        PayConiqApp.appComponent.inject(this)
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpCacheDirectory = File(payCogApp.cacheDir, "responses")

        var cache: Cache? = null
        try {
            cache = Cache(httpCacheDirectory, 10 * 1024 * 1024)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val client = OkHttpClient.Builder()
                .addInterceptor(logging)

                .addInterceptor { chain ->
                    val ongoing = chain.request().newBuilder()
                    ongoing.addHeader("Accept", "application/json;versions=1")

////                    val preference=
//                        val token= pref.getString("AccessToken","")
//                        if (token!=null)
//                            ongoing.addHeader("Authorization", "token "+token)

                    chain.proceed(ongoing.build())
                }
                .cache(cache)
                .build()


        return Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(IRetrofit::class.java)

    }

}