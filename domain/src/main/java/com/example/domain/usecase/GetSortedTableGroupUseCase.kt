package com.example.domain.usecase

import com.example.domain.model.TableGroup
import com.example.domain.model.TeamStats
import com.example.domain.usecase.base.BaseUseCase
import com.example.domain.usecase.base.UseCaseResult

class GetSortedTableGroupUseCase : BaseUseCase<TableGroup, GetSortedTableGroupUseCase.Params>() {

    override suspend fun buildUseCase(params: Params): UseCaseResult<TableGroup> {
        return UseCaseResult.Success(
            params.tableGroup.copy(
                stats = sortStats(params.tableGroup.stats)
            )
        )
    }

    private fun sortStats(stats: List<TeamStats>): List<TeamStats> {
        return stats.sortedWith(
            compareByDescending<TeamStats> { it.points }
                .thenByDescending { it.goalDiff }
                .thenByDescending { it.goalFor }
                .thenBy { it.goalAgainst }
        )
    }

    data class Params(val tableGroup: TableGroup)
}