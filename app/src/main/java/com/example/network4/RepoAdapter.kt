package com.example.network4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.network4.network.dto.RepoDto


class RepoViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val repoName: TextView

    init {
        repoName = view.findViewById(R.id.repo_name)
    }
}

class RepoAdapter(val repoResults: RepoDto): RecyclerView.Adapter<RepoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val repoView = LayoutInflater.from(parent.context).inflate(R.layout.repolistitem, parent, false)
        return RepoViewHolder(repoView)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.repoName.text = repoResults.city_name
    }

    override fun getItemCount(): Int {
        return 0
    }
}