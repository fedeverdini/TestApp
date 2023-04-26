package com.example.presentation.results

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.TeamStats
import com.example.presentation.R
import com.example.presentation.databinding.TeamStatsItemViewBinding

class ResultsAdapter(
    private var teamsStats: List<TeamStats>,
) :
    RecyclerView.Adapter<ResultsAdapter.StatsViewHolder>() {

    companion object {
        private const val NUM_WINNERS = 2
    }

    override fun getItemCount() = teamsStats.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder {
        val binding = TeamStatsItemViewBinding.inflate(LayoutInflater.from(parent.context)).apply {
            root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        return StatsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {
        holder.bind(teamsStats[position], position)
    }

    fun updateData(teamsStats: List<TeamStats>) {
        this.teamsStats = teamsStats
        this.notifyDataSetChanged()
    }

    class StatsViewHolder(private val binding: TeamStatsItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(teamsStats: TeamStats, position: Int) {
            val resources = binding.root.resources
            binding.root.setBackgroundColor(
                resources.getColor(
                    if (position.inc() <= NUM_WINNERS) R.color.green_100 else R.color.green_50,
                    null
                )
            )

            binding.position.text = position.inc().toString()
            binding.teamName.text = teamsStats.team.name
            binding.played.text = teamsStats.played.toString()
            binding.win.text = teamsStats.winCount.toString()
            binding.draw.text = teamsStats.drawCount.toString()
            binding.loss.text = teamsStats.lossCount.toString()
            binding.goalFor.text = teamsStats.goalFor.toString()
            binding.goalAgainst.text = teamsStats.goalAgainst.toString()
            binding.goalDiff.text = teamsStats.goalDiff.toString()
            binding.points.text = teamsStats.points.toString()
        }
    }
}