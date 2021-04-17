package com.example.retrofitcouroutinelivedatanewformat

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitcouroutinelivedatanewformat.model.Repository
import javax.inject.Inject

class ReposAdapter@Inject constructor(
) : PagingDataAdapter<Repository, ReposAdapter.RepoViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            holder.bind(repoItem)
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean =
                oldItem.full_name == newItem.full_name

            override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean =
                oldItem == newItem
        }
    }

    class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.repo_name)
        private val description: TextView = view.findViewById(R.id.repo_description)
        private val stars: TextView = view.findViewById(R.id.repo_stars)
        private val language: TextView = view.findViewById(R.id.repo_language)
        private val forks: TextView = view.findViewById(R.id.repo_forks)

        private var repo: Repository? = null

        init {
            view.setOnClickListener {
                repo?.url?.let { url ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    view.context.startActivity(intent)
                }
            }
        }

        fun bind(repo: Repository?) {
            if (repo == null) {
                val resources = itemView.resources
                name.text = resources.getString(R.string.loading)
                description.visibility = View.GONE
                language.visibility = View.GONE
                stars.text = resources.getString(R.string.unknown)
                forks.text = resources.getString(R.string.unknown)
            } else {
                showRepoData(repo)
            }
        }

        private fun showRepoData(repo: Repository) {
            this.repo = repo
            name.text = repo.full_name

            // if the description is missing, hide the TextView
            var descriptionVisibility = View.GONE
            if (repo.description != null) {
                description.text = repo.description
                descriptionVisibility = View.VISIBLE
            }
            description.visibility = descriptionVisibility

            stars.text = repo.stargazers_count.toString()
            forks.text = repo.forks.toString()

            // if the language is missing, hide the label and the value
            var languageVisibility = View.GONE
            if (!repo.language.isNullOrEmpty()) {
                val resources = this.itemView.context.resources
                language.text = resources.getString(R.string.language, repo.language)
                languageVisibility = View.VISIBLE
            }
            language.visibility = languageVisibility
        }

        companion object {
            fun create(parent: ViewGroup): RepoViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.repo_view_item, parent, false)
                return RepoViewHolder(view)
            }
        }
    }

}
