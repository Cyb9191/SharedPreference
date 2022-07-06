package com.example.network4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.network4.network.dto.AirQualityRepository
import com.example.network4.network.dto.RepoDto
import com.example.network4.network.dto.RepoResult


class RepoViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val repoName: TextView

    init {
        repoName = view.findViewById(R.id.textview_first)
    }
}

class RepoAdapter(val repoResults: List<AirQualityRepository>): RecyclerView.Adapter<RepoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val repoView = LayoutInflater.from(parent.context).inflate(R.layout.repolistitem, parent, false)
        return RepoViewHolder(repoView)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.repoName.text = repoResults[position].city_name
    }

    override fun getItemCount(): Int {
        return 0
    }
}