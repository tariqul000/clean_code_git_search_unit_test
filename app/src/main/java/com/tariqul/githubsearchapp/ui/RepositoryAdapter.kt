package com.tariqul.githubsearchapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tariqul.githubsearchapp.R
import com.tariqul.githubsearchapp.data.model.GitHubRepo

class RepoAdapter(private val repos: List<GitHubRepo>) : RecyclerView.Adapter<RepoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.repo_name)
        val description: TextView = view.findViewById(R.id.repo_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.repo_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = repos[position]
        holder.name.text = repo.name
        holder.description.text = repo.description ?: "No description"
    }

    override fun getItemCount(): Int = repos.size
}