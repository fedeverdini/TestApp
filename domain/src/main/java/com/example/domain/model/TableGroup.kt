package com.example.domain.model

import java.io.Serializable

data class TableGroup(
    val stats: List<TeamStats>
): Serializable