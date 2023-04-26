package com.example.testapp.di

import com.example.data.team.team.repositoryimpl.TeamRepository
import com.example.data.team.team.datasource.TeamStaticDataSource
import com.example.domain.repository.ITeamRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<ITeamRepository> {
        TeamRepository(TeamStaticDataSource())
    }
}