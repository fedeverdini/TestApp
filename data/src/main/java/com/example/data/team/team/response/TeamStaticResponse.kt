package com.example.data.team.team.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class TeamStaticResponse(
    @SerializedName("teams") val teams: List<TeamResponse> = emptyList(),
)

data class TeamResponse(
    @SerializedName("name") val name: String? = null,
    @SerializedName("strength") val strength: Int? = null
)

