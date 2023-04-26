package com.example.presentation.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.TableGroup
import com.example.domain.model.TeamStats
import com.example.domain.usecase.GetSortedTableGroupUseCase
import com.example.domain.utils.getOrNull
import com.example.presentation.utils.DefaultDispatcherProvider
import com.example.presentation.utils.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ResultsViewModel(
    dispatchers: DispatcherProvider = DefaultDispatcherProvider(),
    private val getSortedTableGroupUseCase: GetSortedTableGroupUseCase,
) : ViewModel() {

    private val ioScope = CoroutineScope(dispatchers.io())

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    fun createTableGroup(teamStats: List<TeamStats>) {
        if (teamStats.isNotEmpty()) {
            val tableGroup = TableGroup(teamStats)
            getSortedTableGroup(tableGroup)
        }
    }

    private fun getSortedTableGroup(tableGroup: TableGroup) {
        viewModelScope.launch(ioScope.coroutineContext) {
            getSortedTableGroupUseCase.excecute(
                GetSortedTableGroupUseCase.Params(tableGroup = tableGroup)
            ).getOrNull()?.let { tableGroup ->
                _state.update {
                    it.copy(tableGroup = tableGroup)
                }
            }
        }
    }

    data class State(
        val tableGroup: TableGroup? = null,
    )
}