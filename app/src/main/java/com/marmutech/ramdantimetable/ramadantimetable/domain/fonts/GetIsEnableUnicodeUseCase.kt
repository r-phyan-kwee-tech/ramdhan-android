package com.marmutech.ramdantimetable.ramadantimetable.domain.fonts

import com.marmutech.ramdantimetable.ramadantimetable.domain.FlowUseCase
import com.marmutech.ramdantimetable.ramadantimetable.repository.UserSettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetIsEnableUnicodeUseCase @Inject constructor(private val userSettingRepository: UserSettingRepository) :
    FlowUseCase<Unit, Boolean> {
    override suspend fun execute(t: Unit): Flow<Boolean> = userSettingRepository.getIsEnableUnicode()
}
