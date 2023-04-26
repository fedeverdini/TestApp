package com.example.presentation.group.adapter

import com.example.domain.model.Match

data class GroupUIModel(
    val groupId: Int,
    val matches: List<Match>
) {
    val isComplete = matches.all { it.isFinished }
}