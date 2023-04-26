package com.example.presentation.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.model.Team
import com.example.presentation.R
import com.example.presentation.databinding.TeamsFragmentBinding
import com.example.presentation.group.GroupsFragment
import com.example.presentation.main.MainActivity
import com.example.presentation.utils.GridItemDecorator
import com.example.presentation.utils.collectEvent
import com.example.presentation.utils.collectState
import com.example.presentation.utils.selectVisibility
import org.koin.androidx.viewmodel.ext.android.viewModel

class TeamsFragment : Fragment() {
    companion object {
        private const val NUM_OF_COLUMNS = 2
    }

    private lateinit var binding: TeamsFragmentBinding

    private val viewModel: TeamsViewModel by viewModel()

    private var teamsAdapter: TeamsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!::binding.isInitialized) {
            binding = TeamsFragmentBinding.inflate(layoutInflater)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle()
        initCollectors()
        initListeners()
    }

    private fun setToolbarTitle() {
        (requireActivity() as MainActivity).apply {
            setToolBarTitle(
                requireContext().getString(R.string.tool_bar_teams_title)
            )
        }
    }

    override fun onResume() {
        viewModel.state.value.teams.takeUnless { it.isEmpty() }?.let {
            updateTeams(it)
        }
        super.onResume()
    }

    private fun initCollectors() {
        collectState(viewModel.state) { state -> renderState(state) }
        collectEvent(viewModel.event) { event -> launchEvent(event) }
    }

    private fun renderState(state: TeamsViewModel.State) {
        updateTeams(state.teams)
    }

    private fun launchEvent(event: TeamsViewModel.Event) {
        when (event) {
            is TeamsViewModel.Event.NoTeamsEvent -> showNoTeamsErrorMessage()
        }
    }

    private fun showNoTeamsErrorMessage() {
        Toast.makeText(
            requireContext(),
            getString(R.string.no_teams_error_message),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun initRecyclerView(teams: List<Team>) {
        teamsAdapter = TeamsAdapter(teams, NUM_OF_COLUMNS)

        binding.teamsRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), NUM_OF_COLUMNS)
            adapter = teamsAdapter
            addItemDecoration(GridItemDecorator(margin = 8))
        }
    }

    private fun initListeners() {
        binding.getTeamsButton.setOnClickListener {
            viewModel.getTeams()
        }

        binding.createGroupsButton.setOnClickListener {
            viewModel.state.value.teams.takeUnless { it.isEmpty() }?.let {
                findNavController().navigate(R.id.groupFragment, Bundle().apply {
                    putSerializable(GroupsFragment.ARG_TEAMS_KEY, it.toTypedArray())
                })
            }
        }
    }

    private fun updateTeams(teams: List<Team>) {
        binding.noTeamsImage.selectVisibility(isVisible = teams.isEmpty(), isGone = false)
        binding.teamsRecyclerView.selectVisibility(isVisible = teams.isNotEmpty(), isGone = false)

        teams.takeUnless { it.isEmpty() }?.let {
            teamsAdapter?.updateData(teams) ?: initRecyclerView(teams)
        }
    }
}