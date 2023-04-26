package com.example.presentation.results

import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.TableGroup
import com.example.domain.model.TeamStats
import com.example.presentation.databinding.ResultsFragmentBinding
import com.example.presentation.utils.collectState
import com.example.presentation.utils.dpToPx
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResultsFragment : DialogFragment() {
    private lateinit var binding: ResultsFragmentBinding

    private val viewModel: ResultsViewModel by viewModel()

    private val headersResultsAdapter: HeadersResultsAdapter = HeadersResultsAdapter()
    private var resultsAdapter: ResultsAdapter? = null
    private var concatAdapter: ConcatAdapter? = null

    companion object {
        private const val ARG_TEAM_STATS_KEY = "teamsStats"

        fun newInstance(teamsStats: List<TeamStats>): ResultsFragment {
            return ResultsFragment().apply {
                arguments = bundleOf(
                    ARG_TEAM_STATS_KEY to teamsStats
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ResultsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        binding.root.requestLayout()
        binding.root.invalidate()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val teamStats = arguments?.get(ARG_TEAM_STATS_KEY) as? List<TeamStats>
        teamStats?.toList()?.let {
            viewModel.createTableGroup(it)
        }
        initCollectors()
        initListeners()
    }

    private fun initCollectors() {
        collectState(viewModel.state) { state -> renderState(state) }
    }

    private fun renderState(state: ResultsViewModel.State) {
        updateTableGroup(state.tableGroup)
    }

    private fun updateTableGroup(tableGroup: TableGroup?) {
        tableGroup?.takeUnless { it.stats.isEmpty() }?.let {
            resultsAdapter?.updateData(it.stats) ?: initRecyclerView(it.stats)
        }
    }

    private fun initRecyclerView(stats: List<TeamStats>) {
        resultsAdapter = ResultsAdapter(stats)
        concatAdapter = ConcatAdapter(headersResultsAdapter, resultsAdapter)

        binding.statsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = concatAdapter
            addItemDecoration(
                object : RecyclerView.ItemDecoration() {
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

    private fun initListeners() {
        binding.closeButton.setOnClickListener {
            dismiss()
        }
    }
}