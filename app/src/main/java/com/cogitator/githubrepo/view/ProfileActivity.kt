package com.cogitator.githubrepo.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.cogitator.githubrepo.R
import com.cogitator.githubrepo.model.data.LocalRepository
import com.cogitator.githubrepo.model.data.Repo
import com.cogitator.githubrepo.model.data.UserProfile
import com.cogitator.githubrepo.network.NetworkUtils
import com.cogitator.githubrepo.utils.loading
import com.cogitator.githubrepo.viewModel.UserViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.user_info_image.*
import kotlinx.android.synthetic.main.user_profile.*


/**
 * @author Ankit Kumar (ankitdroiddeveloper@gmail.com) on 12/05/2018 (MM/DD/YYYY)
 */
class ProfileActivity : AppCompatActivity() {
    private var model: UserViewModel? = null
    private var subscriptions = CompositeDisposable()

    private var previousTotal = 0 // The total number of items in the dataset after the last load
    private var loading = true // True if we are still waiting for the last set of data to load.
    private val visibleThreshold = 3 // The minimum amount of items to have below your current scroll position before loading more.
    internal var firstVisibleItem: Int = 0
    internal var visibleItemCount: Int = 0
    internal var totalItemCount: Int = 0

    private var currentPage = 1
    private lateinit var mLinearLayoutManager: LinearLayoutManager
    private lateinit var repoAdapter: RepoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_profile)
//        RealmManager.open()
        setUpToolbar()
        model = ViewModelProviders.of(this).get(UserViewModel::class.java)
        print("Model $model")

    }

    private fun setUpToolbar() {
        val toolbar = toolbar as android.support.v7.widget.Toolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setHomeButtonEnabled(false)
            setDisplayHomeAsUpEnabled(false)
        }
    }


    override fun onResume() {
        super.onResume()
        updateUser()
    }


    private fun updateUser() {
        if (NetworkUtils().isNetworkAvailable(this)) {
            val u = model?.getUserProfile("JakeWharton")
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({
                        //                        saveUser(it)
                        updateProfile(it)
                        print("Response => $it")
                    }, { e ->
                        e.printStackTrace()
                    }, {

                    })
            u?.let { subscriptions.add(it) }
        }
//        else getUserFromDB()?.let { updateProfile(it) }

    }

    private fun updateProfile(userProfile: UserProfile) {
        val fadeIn = createAnimation()
        profile_pic?.loading(userProfile.avatarUrl)

        if (userProfile.name.trim().isBlank()) txt_displayname.visibility = View.GONE else {
            txt_displayname.apply {
                visibility = View.VISIBLE
                startAnimation(fadeIn)
                text = userProfile.name
            }
        }

        txt_login.visibility = View.VISIBLE
        txt_login.text = userProfile.login

        if (userProfile.bio.isNullOrEmpty()) txt_bio?.visibility = View.GONE else {
            txt_bio.apply {
                visibility = View.VISIBLE
                startAnimation(fadeIn)
                text = userProfile.bio
            }
        }
        if (userProfile.company.isNullOrEmpty()) txt_company.visibility = View.GONE else {
            txt_company?.apply {
                visibility = View.VISIBLE
                startAnimation(fadeIn)
                text = userProfile.company
            }
        }
        if (userProfile.location.isNullOrEmpty()) txt_location?.visibility = View.GONE else {
            txt_location?.apply {
                visibility = View.VISIBLE
                startAnimation(fadeIn)
                text = userProfile.location
            }
        }
        if (userProfile.email.isNullOrEmpty()) txt_email?.visibility = View.GONE else {
            txt_email?.apply {
                visibility = View.VISIBLE
                startAnimation(fadeIn)
                text = userProfile.email
            }
        }
        if (userProfile.blog.isNullOrEmpty()) txt_blog?.visibility = View.GONE else {
            txt_blog?.apply {
                visibility = View.VISIBLE
                startAnimation(fadeIn)
                text = userProfile.blog
            }
        }
        updateRepos()
    }

    private fun updateRepos() {
        recyclerView_repo.apply {
            setHasFixedSize(true)
            mLinearLayoutManager = LinearLayoutManager(context)
            recyclerView_repo.layoutManager = mLinearLayoutManager
            repoAdapter = RepoAdapter(null)
            recyclerView_repo.adapter = repoAdapter
            if (NetworkUtils().isNetworkAvailable(context)) {
                model?.getRepository("JakeWharton", currentPage)
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe({
                            //                            LocalRepository().saveRepos(it)
                            updateUI(it)
                        }, { e ->
                            e.printStackTrace()
                        }, {

                        })?.let { subscriptions.add(it) }
                scrollListener()
            }
//            else updateUI(LocalRepository().loadRepos())
        }
    }

    private fun scrollListener() {
        recyclerView_repo.setOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                visibleItemCount = recyclerView.childCount
                totalItemCount = mLinearLayoutManager.itemCount
                firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition()

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false
                        previousTotal = totalItemCount
                    }
                }
                if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
                    // End has been reached

                    // Do something
                    currentPage++

                    onLoadMore()

                    loading = true
                }
            }
        })

    }

    private fun onLoadMore() {
        model?.getRepository("JakeWharton", currentPage)?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({
            updateUI(it)
        }, { e ->
            e.printStackTrace()
        }, {

        })?.let { subscriptions.add(it) }
        loading = true
    }

    private fun updateUI(repos: List<Repo>) {

        with(repoAdapter) {
            if (currentPage == 1)
                clearItems()
            addItems(repos)
        }
    }


    private fun createAnimation(): Animation {
        val fadeIn = AlphaAnimation(0.0f, 1.0f)
        fadeIn.duration = 1200
        fadeIn.fillAfter = true
        return fadeIn
    }

    override fun onDestroy() {
//        RealmManager.close()
        super.onDestroy()

    }

//    private fun saveUser(userProfile: UserProfile) {
//        RealmManager.createUserDao().saveUserProfileData(userProfile)
//    }
//
//    private fun getUserFromDB(): UserProfile? {
//        return RealmManager.createUserDao().getUserProfileData()
//
//    }


}