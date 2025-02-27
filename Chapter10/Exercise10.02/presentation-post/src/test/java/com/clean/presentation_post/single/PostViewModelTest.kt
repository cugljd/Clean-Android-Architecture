package com.clean.presentation_post.single

import com.clean.domain.entity.Result
import com.clean.domain.usecase.GetPostUseCase
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

class PostViewModelTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher = StandardTestDispatcher()
    private val useCase = mock<GetPostUseCase>()
    private val converter = mock<PostConverter>()
    private lateinit var viewModel: PostViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() = runTest {
        Dispatchers.setMain(testDispatcher)
        viewModel = PostViewModel(useCase, converter)
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
        val postId = 1L
        val uiState = mock<UiState<PostModel>>()
        val result = mock<Result<GetPostUseCase.Response>>()
        whenever(useCase.execute(GetPostUseCase.Request(postId))).thenReturn(
            flowOf(
                result
            )
        )
        whenever(converter.convert(result)).thenReturn(uiState)
        viewModel.handleAction(PostUiAction.Load(postId))
        assertEquals(uiState, viewModel.uiStateFlow.value)
    }
}