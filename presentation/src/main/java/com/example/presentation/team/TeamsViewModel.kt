package com.example.presentation.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Team
import com.example.domain.usecase.GetTeamsUseCase
import com.example.domain.utils.getOrNull
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

class TeamsViewModel(
    dispatchers: DispatcherProvider,
    private val getTeamsUseCase: GetTeamsUseCase
) : ViewModel() {

    companion object {
        private const val NUM_OF_TEAMS = 4
    }

    private val ioScope = CoroutineScope(dispatchers.io())

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> = _event.asSharedFlow()

    fun getTeams() {
        viewModelScope.launch(ioScope.coroutineContext) {
            getTeamsUseCase.excecute(GetTeamsUseCase.Params(NUM_OF_TEAMS)).getOrNull()
                ?.let { teams ->
                    _state.update { state ->
                        state.copy(teams = teams)
                    }
                } ?: run {
                emitEvents(Event.NoTeamsEvent)
            }
        }
    }

    private fun emitEvents(vararg events: Event) {
        viewModelScope.launch {
            events.forEach { _event.emit(it) }
        }
    }

    data class State(
        val teams: List<Team> = emptyList(),
    )

    sealed class Event {
        object NoTeamsEvent : Event()
    }
}