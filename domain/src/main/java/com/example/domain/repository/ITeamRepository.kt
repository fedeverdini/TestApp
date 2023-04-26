package com.example.domain.repository

import com.example.domain.model.Team

interface ITeamRepository {
    fun getTeams(): List<Team>
}