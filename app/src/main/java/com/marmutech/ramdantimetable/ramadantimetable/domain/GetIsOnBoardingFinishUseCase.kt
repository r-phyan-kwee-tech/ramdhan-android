package com.marmutech.ramdantimetable.ramadantimetable.domain

import com.marmutech.ramdantimetable.ramadantimetable.repository.UserSettingRepository
import javax.inject.Inject

class GetIsOnBoardingFinishUseCase @Inject constructor(private val userSettingRepository: UserSettingRepository) :
    CoroutineUseCase<Unit, Boolean> {
    override suspend fun execute(param: Unit): Boolean = userSettingRepository.getIsOnBoardingFinish()
}
