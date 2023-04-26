package com.example.presentation.group.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Match
import com.example.presentation.R
import com.example.presentation.databinding.GroupViewItemBinding

class GroupsAdapter(
    private var groups: MutableList<GroupUIModel>,
    private var onPlayClicked: (Int, Match) -> Unit
) :
    RecyclerView.Adapter<GroupsAdapter.GroupViewHolder>() {

    override fun getItemCount() = groups.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val binding = GroupViewItemBinding.inflate(LayoutInflater.from(parent.context))
        return GroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind(groups[position], onPlayClicked)
    }

    fun updateData(groups: List<GroupUIModel>) {
        this.groups.clear()
        this.groups.addAll(groups)
        this.notifyDataSetChanged()
    }

    class GroupViewHolder(private val binding: GroupViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(group: GroupUIModel, onPlayClicked: (Int, Match) -> Unit) {
            val resources = binding.root.resources
            binding.groupName.text =
                String.format(resources.getString(R.string.group_name), group.groupId)

            binding.cardView.setCardBackgroundColor(
                resources.getColor(
                    R.color.green_50,
                    null
                )
            )

            // TODO: convert in recycler view
            binding.matches.removeAllViews()
            group.matches.forEach { match ->
                val matchView = GroupMatchView(binding.root.context)
                matchView.bind(group.groupId, match, onPlayClicked)
                binding.matches.addView(matchView)
            }
        }
    }
}