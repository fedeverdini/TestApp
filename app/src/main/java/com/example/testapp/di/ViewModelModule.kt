package com.example.testapp.di

import android.support.v4.os.IResultReceiver.Default
import com.example.presentation.group.GroupsViewModel
import com.example.presentation.results.ResultsViewModel
import com.example.presentation.team.TeamsViewModel
import com.example.presentation.utils.DefaultDispatcherProvider
import com.example.presentation.utils.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        TeamsViewModel(
            DefaultDispatcherProvider(),
            get(named("GetTeamsUseCase"))
        )
    }

    viewModel {
        GroupsViewModel(
            DefaultDispatcherProvider(),
            get(named("GetGroupsUseCase")),
            get(named("SimulateMatchUseCase"))
        )
    }

    viewModel {
        ResultsViewModel(
            DefaultDispatcherProvider(),
            get(named("GetSortedTableGroupUseCase"))
        )
    }
}