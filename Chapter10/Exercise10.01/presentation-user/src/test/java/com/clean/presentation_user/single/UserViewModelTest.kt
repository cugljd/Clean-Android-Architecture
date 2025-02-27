package com.clean.presentation_user.single

import com.clean.domain.entity.Result
import com.clean.domain.usecase.GetUserUseCase
import com.clean.presentation_common.state.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class UserViewModelTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher = StandardTestDispatcher()
    private val useCase = mock<GetUserUseCase>()
    private val converter = mock<UserConverter>()
    private lateinit var viewModel: UserViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() = runTest {
        Dispatchers.setMain(testDispatcher)
        viewModel = UserViewModel(useCase, converter)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testHandleActionLoad() = runTest {
        assertEquals(UiState.Loading, viewModel.uiStateFlow.value)
        val userId = 1L
        val uiState = mock<UiState<UserModel>>()
        val result = mock<Result<GetUserUseCase.Response>>()
        whenever(useCase.execute(GetUserUseCase.Request(userId))).thenReturn(
            flowOf(
                result
            )
        )
        whenever(converter.convert(result)).thenReturn(uiState)
        viewModel.handleAction(UserUiAction.Load(userId))
        assertEquals(uiState, viewModel.uiStateFlow.value)
    }
}