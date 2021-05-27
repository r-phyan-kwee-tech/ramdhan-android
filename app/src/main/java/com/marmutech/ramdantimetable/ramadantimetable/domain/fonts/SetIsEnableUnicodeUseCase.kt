package com.marmutech.ramdantimetable.ramadantimetable.domain.fonts

import com.marmutech.ramdantimetable.ramadantimetable.domain.CoroutineUseCase
import com.marmutech.ramdantimetable.ramadantimetable.repository.UserSettingRepository
import javax.inject.Inject

class SetIsEnableUnicodeUseCase @Inject constructor(private val userSettingRepository: UserSettingRepository) :
    CoroutineUseCase<Boolean, Unit> {
    override suspend fun execute(t: Boolean) = userSettingRepository.setIsEnableUnicode(t)
}
