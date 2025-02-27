package com.clean.exercise201

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class NumberAdderTest {

    @get:Rule
    val dispatcherTestRule = DispatcherTestRule()

    @ExperimentalCoroutinesApi
    @Test
    fun testAdd() = runTest {
        val adder = NumberAdder(dispatcherTestRule.testDispatcher, 0)
        assertEquals(5, adder.add(1, 4))
    }
}