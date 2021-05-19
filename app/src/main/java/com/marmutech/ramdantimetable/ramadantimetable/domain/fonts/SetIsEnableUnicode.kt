package com.marmutech.ramdantimetable.ramadantimetable.domain.fonts

import com.marmutech.ramdantimetable.ramadantimetable.domain.FlowUseCase
import com.marmutech.ramdantimetable.ramadantimetable.repository.UserSettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetIsEnableUnicode @Inject constructor(private val userSettingRepository: UserSettingRepository) :
    FlowUseCase<Boolean, Boolean> {
    override suspend fun execute(t: Boolean): Flow<Boolean> = userSettingRepository.setIsEnableUnicode(
        t
    )
}
