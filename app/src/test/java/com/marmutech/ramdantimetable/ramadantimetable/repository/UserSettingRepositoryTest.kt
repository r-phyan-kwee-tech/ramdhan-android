package com.marmutech.ramdantimetable.ramadantimetable.repository

import com.marmutech.ramdantimetable.ramadantimetable.util.UserPrefUtil
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
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

    @Test
    fun setUnicodeEnableTest() = runBlocking {
        val result = repo.getIsEnableUnicode().single()
        assertEquals(true, result)
    }

    @Test
    fun getUnicodeEnableTest() = runBlocking {
        val result = repo.setIsEnableUnicode(enableUnicode)
        assertEquals(enableUnicode, enableUnicode)
    }

    companion object {
        private const val enableUnicode = true
    }

}
