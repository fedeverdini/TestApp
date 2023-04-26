package com.example.presentation.results

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.allViews
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.R
import com.example.presentation.databinding.TeamStatsItemViewBinding

class HeadersResultsAdapter : RecyclerView.Adapter<HeadersResultsAdapter.HeaderViewHolder>() {

    override fun getItemCount() = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val binding = TeamStatsItemViewBinding.inflate(LayoutInflater.from(parent.context)).apply {
            root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        return HeaderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind()
    }

    class HeaderViewHolder(private val binding: TeamStatsItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            with(binding.root.resources) {

                binding.root.setBackgroundColor(
                    getColor(R.color.red_50, null)
                )

                binding.root.allViews.forEach {
                    (it as? TextView)?.typeface = Typeface.DEFAULT_BOLD
                }

                binding.position.text = getString(R.string.table_result_position)
                binding.teamName.text = getString(R.string.table_result_team_name)
                binding.played.text = getString(R.string.table_result_played)
                binding.win.text = getString(R.string.table_result_win)
                binding.draw.text = getString(R.string.table_result_draw)
                binding.loss.text = getString(R.string.table_result_loss)
                binding.goalFor.text = getString(R.string.table_result_for)
                binding.goalAgainst.text = getString(R.string.table_result_against)
                binding.goalDiff.text = getString(R.string.table_result_diff)
                binding.points.text = getString(R.string.table_result_points)
            }
        }
    }
}