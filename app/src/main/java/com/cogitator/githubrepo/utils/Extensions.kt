package com.cogitator.githubrepo.utils

import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.cogitator.githubrepo.R

/**
 * @author Ankit Kumar (ankitdroiddeveloper@gmail.com) on 12/05/2018 (MM/DD/YYYY)
 */

fun ImageView.loading(imageUrl: String) {
    if (TextUtils.isEmpty(imageUrl)) {
        Glide.with(context).load(R.mipmap.ic_launcher).into(this)
    } else {
        Glide.with(context).load(imageUrl).into(this)
    }
}