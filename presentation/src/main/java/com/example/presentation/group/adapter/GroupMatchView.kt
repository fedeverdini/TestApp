package com.example.presentation.group.adapter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.domain.model.Match
import com.example.presentation.R
import com.example.presentation.databinding.GroupMatchComponentBinding
import com.example.presentation.utils.selectVisibility

class GroupMatchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttrs) {
    private val binding: GroupMatchComponentBinding = GroupMatchComponentBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    fun bind(
        group: Int,
        match: Match,
        onPlayClick: (Int, Match) -> Unit
    ) {
        binding.homeTeam.text = match.homeTeam.name
        binding.awayTeam.text = match.awayTeam.name

        binding.matchResult.text = String.format(
            context.getString(R.string.match_score_result),
            match.homeScore,
            match.awayScore
        )

        binding.playButton.selectVisibility(match.isFinished.not(), false)
        binding.matchResult.selectVisibility(match.isFinished, false)

        binding.playButton.setOnClickListener {
            onPlayClick.invoke(group, match)
        }
    }
}