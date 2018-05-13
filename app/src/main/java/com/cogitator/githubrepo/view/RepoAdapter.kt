package com.cogitator.githubrepo.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cogitator.githubrepo.R
import com.cogitator.githubrepo.model.data.Repo
import kotlinx.android.synthetic.main.repo_item.view.*

/**
 * @author Ankit Kumar (ankitdroiddeveloper@gmail.com) on 12/05/2018 (MM/DD/YYYY)
 */
class RepoAdapter(val viewActions: onViewSelectedListener?) : RecyclerView.Adapter<RepoAdapter.ViewHolder>() {
    private var datas = ArrayList<Repo>()

    interface onViewSelectedListener {
        fun onItemSelected(url: String?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflator = LayoutInflater.from(parent?.context)
        val view = inflator.inflate(R.layout.repo_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    fun getAllItems(): List<Repo> {
        return datas
    }

    fun addItems(items: List<Repo>) {
        datas.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        datas.clear()

    }

    override fun getItemCount(): Int {
        return datas.size
    }


    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Repo) = with(itemView) {

            txt_display_name.text = item.fullName
            txt_bio.text = item.language
            //comments.text = """${12} comments"""
            txt_repoStars.text = item.stargazersCount.toString()
            txt_repoForks.text = item.forksCount.toString()


            itemView.setOnClickListener {
                viewActions?.onItemSelected(item.url)
            }
        }
    }
}