package com.marmutech.ramdantimetable.ramadantimetable.domain.fonts

import com.marmutech.ramdantimetable.ramadantimetable.domain.CoroutineUseCase
import com.marmutech.ramdantimetable.ramadantimetable.repository.UserSettingRepository
import javax.inject.Inject

class GetIsEnableUnicodeUseCase @Inject constructor(private val userSettingRepository: UserSettingRepository) :
    CoroutineUseCase<Unit, Boolean> {
    override suspend fun execute(t: Unit): Boolean = userSettingRepository.getIsEnableUnicode()
}
