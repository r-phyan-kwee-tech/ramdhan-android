package com.marmutech.ramdantimetable.ramadantimetable.ui.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marmutech.ramdantimetable.ramadantimetable.domain.country.GetCountryListUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.country.GetSelectedCountryIdUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.country.SaveSelectedCountryIdUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.fonts.GetIsEnableUnicodeUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.fonts.SetIsEnableUnicodeUseCase
import com.marmutech.ramdantimetable.ramadantimetable.domain.state.GetStateListBySelectedCountryUseCase
import com.nhaarman.mockito_kotlin.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.only
import org.mockito.kotlin.verify

@RunWith(JUnit4::class)
class SplashViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var vm: SplashViewModel

    private val setIsEnableUnicodeUseCase: SetIsEnableUnicodeUseCase = mock { }
    private val getIsEnableUnicodeUseCase: GetIsEnableUnicodeUseCase = mock { }
    private val getStateListBySelectedCountryUseCase: GetStateListBySelectedCountryUseCase = mock { }
    private val getCountryListUseCase: GetCountryListUseCase = mock { }
    private val getSelectedCountryIdUseCase: GetSelectedCountryIdUseCase = mock { }
    private val saveSelectedCountryIdUseCase: SaveSelectedCountryIdUseCase = mock { }

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        vm = SplashViewModel(
            testDispatcher,
            setIsEnableUnicodeUseCase,
            getIsEnableUnicodeUseCase,
            saveSelectedCountryIdUseCase,
            getSelectedCountryIdUseCase,
            getCountryListUseCase,
            getStateListBySelectedCountryUseCase
        )
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testSetSelectedCountryId() = testDispatcher.runBlockingTest {
        vm.setSelectedCountryId("123")
        verify(saveSelectedCountryIdUseCase, only()).execute("123")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testSetEnableUnicode() = testDispatcher.runBlockingTest {
        vm.setEnableUnicode(true)
        verify(setIsEnableUnicodeUseCase, only()).execute(true)
    }
}
