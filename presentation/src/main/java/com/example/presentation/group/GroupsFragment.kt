package com.example.presentation.group

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.domain.model.Match
import com.example.presentation.R
import com.example.presentation.databinding.GroupFragmentBinding
import com.example.presentation.group.adapter.GroupUIMapper
import com.example.presentation.group.adapter.GroupUIModel
import com.example.presentation.group.adapter.GroupsAdapter
import com.example.presentation.main.MainActivity
import com.example.presentation.results.ResultsFragment
import com.example.presentation.utils.collectEvent
import com.example.presentation.utils.collectState
import com.example.presentation.utils.dpToPx
import com.example.presentation.utils.selectVisibility
import com.example.presentation.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class GroupsFragment : Fragment() {
    private lateinit var binding: GroupFragmentBinding

    private val viewModel: GroupsViewModel by viewModel()

    private var groupsAdapter: GroupsAdapter? = null

    private val args: GroupsFragmentArgs by navArgs()

    companion object {
        const val ARG_TEAMS_KEY = "teams" // from nav_graph
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!::binding.isInitialized) {
            binding = GroupFragmentBinding.inflate(layoutInflater)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getGroups(args.teams.toList())
        setToolbarTitle()
        initCollectors()
        initListeners()
    }

    private fun setToolbarTitle() {
        (requireActivity() as MainActivity).apply {
            setToolBarTitle(
                requireContext().getString(R.string.tool_bar_group_title)
            )
        }
    }

    private fun initCollectors() {
        collectState(viewModel.state) { state -> renderState(state) }
        collectEvent(viewModel.event) { event -> launchEvent(event) }
    }

    private fun renderState(state: GroupsViewModel.State) {
        updateGroups(state.groups)
    }

    private fun launchEvent(event: GroupsViewModel.Event) {
        when (event) {
            GroupsViewModel.Event.GroupPhaseFinished -> finishGroupPhase()
        }
    }

    private fun updateGroups(groups: List<GroupUIModel>) {
        binding.groupsRecyclerView.selectVisibility(isVisible = groups.isNotEmpty(), isGone = false)

        groups.takeUnless { it.isEmpty() }?.let {
            groupsAdapter?.updateData(groups) ?: initRecyclerView(groups)
        }
    }

    private fun initRecyclerView(groups: List<GroupUIModel>) {
        groupsAdapter = GroupsAdapter(groups.toMutableList(), ::onPlayClicked)

        binding.groupsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = groupsAdapter
            addItemDecoration(
                object : ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        val position = parent.getChildAdapterPosition(view)
                        parent.adapter?.itemCount?.dec()?.let { lastPosition ->
                            if (position <= lastPosition) {
                                outRect.bottom = dpToPx(8)
                            }
                        } ?: kotlin.run { outRect.bottom = dpToPx(8) }
                    }
                }
            )
        }
    }

    private fun finishGroupPhase() {
        showResultTable()
        binding.newSimulationButton.visible()
    }

    private fun onPlayClicked(group: Int, match: Match) {
        viewModel.simulateMatch(group, match)
    }

    private fun showResultTable() {
        val groups = viewModel.state.value.groups
        args.teams.map { team ->
            GroupUIMapper.fromGroupUIModelListToTeamStats(team, groups)
        }.takeUnless { it.isEmpty() }?.let { teamsStats ->
            val resultsFragment = ResultsFragment.newInstance(teamsStats)
            resultsFragment.show(childFragmentManager, "resultFragmentDialog")
        }
    }

    private fun initListeners() {
        binding.resultTableButton.setOnClickListener {
            showResultTable()
        }

        binding.newSimulationButton.setOnClickListener {
            findNavController().navigate(
                GroupsFragmentDirections.fromGroupFragmentToTeamsFragmentAction()
            )
        }
    }
}