package com.marmutech.ramdantimetable.ramadantimetable.repository

import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.mock

@RunWith(JUnit4::class)
class UserSettingRepositoryTest {

    private val userPrefUtil: UserPrefUtil = mock {
        on { getFont() }.thenReturn(true)
    }

    private lateinit var repo: UserSettingRepository

    @Before
    fun setUp() {
        repo = UserSettingRepositoryImpl(userPrefUtil)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun setUnicodeEnableTest() = runBlockingTest {
        val result = repo.getIsEnableUnicode()
        assertEquals(true, result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getUnicodeEnableTest() = runBlockingTest {
        val result = repo.setIsEnableUnicode(enableUnicode)
        assertEquals(enableUnicode, enableUnicode)
    }

    companion object {
        private const val enableUnicode = true
    }

}
