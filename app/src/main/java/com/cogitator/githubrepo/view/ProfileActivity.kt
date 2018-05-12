package com.cogitator.githubrepo.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.cogitator.githubrepo.R
import com.cogitator.githubrepo.model.Repo
import com.cogitator.githubrepo.model.UserProfile
import com.cogitator.githubrepo.utils.loading
import com.cogitator.githubrepo.viewModel.UserViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.user_info.*
import kotlinx.android.synthetic.main.user_info_image.*
import kotlinx.android.synthetic.main.user_profile.*

/**
 * @author Ankit Kumar (ankitdroiddeveloper@gmail.com) on 12/05/2018 (MM/DD/YYYY)
 */
class ProfileActivity : AppCompatActivity() {
    private var model: UserViewModel? = null
    private var subscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_profile)
//        setUpToolbar()
        model = ViewModelProviders.of(this).get(UserViewModel::class.java)
        print("Model $model")

    }

    private fun setUpToolbar() {

//        val toolbar = toolbar as android.support.v7.widget.Toolbar
//        toolbar.title = ""
//        setSupportActionBar(toolbar)
//        supportActionBar?.apply {
//            setDisplayShowTitleEnabled(false)
//            setHomeButtonEnabled(false)
//            setDisplayHomeAsUpEnabled(false)
//        }
    }


    override fun onResume() {
        super.onResume()
        updateUser()
    }


    private fun updateUser() {
        val u = model?.getUserProfile("JakeWharton")
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    updateProfile(it)
                    print("Response => $it")
                }, { e ->
                    e.printStackTrace()
                }, {

                })
        u?.let { subscriptions.add(it) }

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
            layoutManager = LinearLayoutManager(context)
            adapter = RepoAdapter(null)

            model?.getRepository("JakeWharton")?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({
                updateUI(it)
            }, { e ->
                e.printStackTrace()
            }, {

            })?.let { subscriptions.add(it) }

        }
    }

    private fun updateUI(repos: List<Repo>) {
        if (recyclerView_repo.adapter == null)
            recyclerView_repo.adapter = RepoAdapter(null)
        val adapter = recyclerView_repo.adapter as RepoAdapter

        with(adapter) {
            clearItems()
            addItems(repos)
            // showLoader(false)

        }
    }

    private fun createAnimation(): Animation {
        val fadeIn = AlphaAnimation(0.0f, 1.0f)
        fadeIn.duration = 1200
        fadeIn.fillAfter = true
        return fadeIn
    }
}