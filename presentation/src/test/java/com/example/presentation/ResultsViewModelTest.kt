package com.example.presentation

import com.example.domain.usecase.GetSortedTableGroupUseCase
import com.example.presentation.results.ResultsViewModel
import org.junit.Rule
import org.mockito.kotlin.mock

class ResultsViewModelTest {
    @get: Rule
    val coroutineRule = CoroutinesTestRule()

    private val getSortedTableGroupUseCase = mock<GetSortedTableGroupUseCase>()

    private val viewModel = ResultsViewModel(
        coroutineRule.testDispatcherProvider,
        getSortedTableGroupUseCase
    )

    // TODO: add test cases
}