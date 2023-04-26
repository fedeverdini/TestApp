package com.example.domain.mocks

import com.example.domain.model.Match
import com.example.domain.model.TableGroup
import com.example.domain.model.Team
import com.example.domain.model.TeamStats

object MockedModels {
    val TEAM_A = Team("Team A", 1)
    val TEAM_B = Team("Team B", 2)
    val TEAM_C = Team("Team C", 3)
    val TEAM_D = Team("Team D", 4)
    val TEAM_E = Team("Team E", 5)
    val TEAM_F = Team("Team F", 6)
    val TEAM_G = Team("Team G", 7)
    val TEAM_H = Team("Team H", 8)

    val ALL_TEAMS = listOf(TEAM_A, TEAM_B, TEAM_C, TEAM_D, TEAM_E, TEAM_F, TEAM_G, TEAM_H)

    val MATCH_A_B = Match(TEAM_A, TEAM_B)

    val MATCH_E_F = Match(TEAM_E, TEAM_F, 2, 7, true)
    val MATCH_E_G = Match(TEAM_E, TEAM_G, 3, 0, true)
    val MATCH_E_H = Match(TEAM_E, TEAM_H, 1, 4, true)
    val MATCH_F_G = Match(TEAM_F, TEAM_G, 0, 3, true)
    val MATCH_F_H = Match(TEAM_F, TEAM_H, 0, 5, true)
    val MATCH_G_H = Match(TEAM_G, TEAM_H, 8, 0, true)

    val TEAM_STATS_E = TeamStats(TEAM_E, listOf(MATCH_E_F, MATCH_E_G, MATCH_E_H))
    val TEAM_STATS_F = TeamStats(TEAM_F, listOf(MATCH_E_F, MATCH_F_G, MATCH_F_H))
    val TEAM_STATS_G = TeamStats(TEAM_G, listOf(MATCH_G_H, MATCH_F_G, MATCH_E_G))
    val TEAM_STATS_H = TeamStats(TEAM_H, listOf(MATCH_G_H, MATCH_F_H, MATCH_E_H))

    val TABLE_GROUP = TableGroup(
        listOf(
            TEAM_STATS_E,
            TEAM_STATS_F,
            TEAM_STATS_G,
            TEAM_STATS_H,
        )
    )

    val SORTED_TABLE_GROUP = TableGroup(
        listOf(
            TEAM_STATS_G, // Pts 6 - GD 10
            TEAM_STATS_H, // Pts 6 - GD -2
            TEAM_STATS_F, // Pts 3 - GD -3
            TEAM_STATS_E  // Pts 3 - GD -5
        )
    )
}