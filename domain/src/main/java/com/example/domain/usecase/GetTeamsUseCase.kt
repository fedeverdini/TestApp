package com.example.domain.usecase

import com.example.domain.model.Team
import com.example.domain.repository.ITeamRepository
import com.example.domain.usecase.base.BaseUseCase
import com.example.domain.usecase.base.UseCaseResult

class GetTeamsUseCase(
    private val teamRepository: ITeamRepository
) : BaseUseCase<List<Team>, GetTeamsUseCase.Params>() {

    override suspend fun buildUseCase(params: Params): UseCaseResult<List<Team>> {
        val teams = teamRepository.getTeams()
        return if (teams.isEmpty()) {
            UseCaseResult.Error(Exception("No teams found"))
        } else {
            UseCaseResult.Success(
                data = teams.shuffled().take(params.numberOfTeams)
            )
        }
    }

    data class Params(val numberOfTeams: Int)
}
