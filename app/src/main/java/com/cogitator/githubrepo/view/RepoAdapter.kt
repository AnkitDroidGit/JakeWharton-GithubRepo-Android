package com.cogitator.githubrepo.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
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
class RepoAdapter : RecyclerView.Adapter<RepoAdapter.ViewHolder>() {
    private var datas = ArrayList<Repo>()
    private val unfoldedIndexes = HashSet<Int>()
    private var defaultRequestBtnClickListener: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val view = inflator.inflate(R.layout.repo_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position], position)
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

    fun getDefaultRequestBtnClickListener(): View.OnClickListener? {
        return defaultRequestBtnClickListener
    }

    fun setDefaultRequestBtnClickListener(defaultRequestBtnClickListener: View.OnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Repo, position: Int) = with(itemView) {

            txt_display_name.text = item.fullName
            txt_display_name_.text = item.fullName
            txt_bio_.text = item.description
            txt_lang_.text = item.language
            txt_repoStars_.text = item.stargazersCount.toString()
            txt_repoForks_.text = item.forksCount.toString()
            txt_created.text = item.watchersCount.toString()
            txt_updated_.text = "Last updated at :" + item.updatedAt
            txt_url_.text = item.htmlUrl
            txt_url_.setOnClickListener({
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(item.htmlUrl))
                context?.startActivity(i)
            })

            if (unfoldedIndexes.contains(position)) {
                cell_title_view.visibility = View.GONE
                cell_content_view.visibility = View.VISIBLE
            } else {
                cell_content_view.visibility = View.GONE
                cell_title_view.visibility = View.VISIBLE
            }

            if (item.requestBtnClickListener != null) {
                itemView.setOnClickListener(item.requestBtnClickListener)
            } else {
                itemView.setOnClickListener(defaultRequestBtnClickListener)
            }
            itemView.setOnClickListener({
                // toggle clicked cell state
                folding_cell.toggle(false)
                // register in adapter that state for selected cell is toggled
                registerToggle(position)
            })
        }

        private fun registerToggle(position: Int) {
            if (unfoldedIndexes.contains(position))
                registerFold(position)
            else
                registerUnfold(position)
        }

        private fun registerFold(position: Int) {
            unfoldedIndexes.remove(position)
        }

        private fun registerUnfold(position: Int) {
            unfoldedIndexes.add(position)
        }
    }
}