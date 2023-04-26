package com.example.presentation.team

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Team
import com.example.presentation.R
import com.example.presentation.databinding.TeamViewItemBinding

class TeamsAdapter(
    private var teams: List<Team>, private val spanCount: Int
) : RecyclerView.Adapter<TeamsAdapter.TeamViewHolder>() {

    override fun getItemCount() = teams.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val binding = TeamViewItemBinding.inflate(LayoutInflater.from(parent.context))
        return TeamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(teams[position], spanCount)
    }

    fun updateData(teams: List<Team>) {
        this.teams = teams
        this.notifyDataSetChanged()
    }

    class TeamViewHolder(private val binding: TeamViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(team: Team, spanCount: Int) {
            val maxSize =
                (binding.root.resources.displayMetrics.widthPixels * 0.9 / spanCount).toInt()
            binding.teamIcon.layoutParams.height = maxSize
            binding.teamName.text = team.name.uppercase()
            binding.teamStrength.text = String.format(
                binding.root.resources.getString(R.string.team_strength), team.strength
            )
        }
    }
}