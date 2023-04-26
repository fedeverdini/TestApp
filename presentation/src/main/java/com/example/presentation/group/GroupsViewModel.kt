package com.example.presentation.group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Match
import com.example.domain.model.Team
import com.example.domain.usecase.GetGroupsUseCase
import com.example.domain.usecase.SimulateMatchUseCase
import com.example.domain.utils.getOrNull
import com.example.presentation.group.adapter.GroupUIMapper
import com.example.presentation.group.adapter.GroupUIModel
import com.example.presentation.utils.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GroupsViewModel(
    dispatchers: DispatcherProvider,
    private val getGroupsUseCase: GetGroupsUseCase,
    private val simulateMatchUseCase: SimulateMatchUseCase,
) : ViewModel() {

    private val ioScope = CoroutineScope(dispatchers.io())

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> = _event.asSharedFlow()

    fun getGroups(teams: List<Team>) {
        viewModelScope.launch(ioScope.coroutineContext) {
            getGroupsUseCase.excecute(GetGroupsUseCase.Params(teams = teams)).getOrNull()
                ?.let { groups ->
                    _state.update {
                        it.copy(groups = GroupUIMapper.fromMapGroupToGroupUIModel(groups))
                    }
                }
        }
    }

    fun simulateMatch(groupId: Int, originalMatch: Match) {
        viewModelScope.launch(ioScope.coroutineContext) {
            simulateMatchUseCase.excecute(SimulateMatchUseCase.Params(originalMatch)).getOrNull()
                ?.let { simulatedMatch ->
                    val groups = state.value.groups.toMutableList()
                    val groupIndex = groups.indexOfFirst { it.groupId == groupId }
                    val matches = groups[groupIndex].matches.toMutableList()
                    val matchIndex = matches.indexOf(originalMatch)

                    matches[matchIndex] = simulatedMatch
                    groups[groupIndex] = GroupUIModel(groupId, matches)

                    _state.update { it.copy(groups = groups) }

                    if (groups.all { it.isComplete }) emitEvents(Event.GroupPhaseFinished)
                }
        }
    }

    private fun emitEvents(vararg events: Event) {
        viewModelScope.launch {
            events.forEach { _event.emit(it) }
        }
    }

    data class State(
        val groups: List<GroupUIModel> = emptyList(),
    )

    sealed class Event {
        object GroupPhaseFinished : Event()
    }
}