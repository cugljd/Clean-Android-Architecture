package com.clean.presentation_common.state

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class MviViewModelTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: MviViewModel<String, UiState<String>, UiAction, UiSingleEvent>

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() = runTest {
        Dispatchers.setMain(testDispatcher)
        viewModel = object : MviViewModel<String, UiState<String>, UiAction, UiSingleEvent>() {
            override fun initState(): UiState<String> = UiState.Loading

            override fun handleAction(action: UiAction) {
            }
        }

    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testSubmitAction() = runTest {
        val uiAction = mock<UiAction>()
        viewModel = object : MviViewModel<String, UiState<String>, UiAction, UiSingleEvent>() {
            override fun initState(): UiState<String> = UiState.Loading

            override fun handleAction(action: UiAction) {
                assertEquals(uiAction, action)
            }
        }
        viewModel.submitAction(mock<UiAction>())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testSubmitState() = runTest {
        val uiState = UiState.Success("test")
        viewModel.submitState(uiState)
        assertEquals(uiState, viewModel.uiStateFlow.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testSubmitSingleEvent() = runTest {
        val uiSingleEvent = mock<UiSingleEvent>()
        viewModel.submitSingleEvent(uiSingleEvent)
        assertEquals(uiSingleEvent, viewModel.singleEventFlow.first())
    }
}