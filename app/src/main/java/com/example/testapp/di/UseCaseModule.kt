package com.example.testapp.di

import com.example.domain.usecase.GetGroupsUseCase
import com.example.domain.usecase.GetHomeTeamWinProbabilityUseCase
import com.example.domain.usecase.GetScoresUseCase
import com.example.domain.usecase.GetSortedTableGroupUseCase
import com.example.domain.usecase.GetTeamsUseCase
import com.example.domain.usecase.IsDrawUseCase
import com.example.domain.usecase.SimulateMatchUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCaseModule = module {
    single(named("GetGroupsUseCase")) {
        GetGroupsUseCase()
    }

    single(named("GetSortedTableGroupUseCase")) {
        GetSortedTableGroupUseCase()
    }

    single(named("GetTeamsUseCase")) {
        GetTeamsUseCase(get())
    }

    single(named("GetHomeTeamWinProbabilityUseCase")) {
        GetHomeTeamWinProbabilityUseCase()
    }

    single(named("IsDrawUseCase")) {
        IsDrawUseCase()
    }

    single(named("GetScoresUseCase")) {
        GetScoresUseCase()
    }

    single(named("SimulateMatchUseCase")) {
        SimulateMatchUseCase(
            get(named("IsDrawUseCase")),
            get(named("GetScoresUseCase")),
            get(named("GetHomeTeamWinProbabilityUseCase"))
        )
    }
}