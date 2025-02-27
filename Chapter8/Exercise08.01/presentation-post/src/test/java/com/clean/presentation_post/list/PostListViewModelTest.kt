package com.clean.presentation_post.list

import com.clean.domain.entity.Result
import com.clean.domain.usecase.GetPostsWithUsersWithInteractionUseCase
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

class PostListViewModelTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher = StandardTestDispatcher()
    private val useCase = mock<GetPostsWithUsersWithInteractionUseCase>()
    private val converter = mock<PostListConverter>()
    private val viewModel = PostListViewModel(useCase, converter)

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testLoadPosts() = runTest {
        assertEquals(UiState.Loading, viewModel.postListFlow.value)
        val uiState = mock<UiState<PostListModel>>()
        val result = mock<Result<GetPostsWithUsersWithInteractionUseCase.Response>>()
        whenever(useCase.execute(GetPostsWithUsersWithInteractionUseCase.Request)).thenReturn(
            flowOf(
                result
            )
        )
        whenever(converter.convert(result)).thenReturn(uiState)
        viewModel.loadPosts()
        assertEquals(uiState, viewModel.postListFlow.value)
    }
}